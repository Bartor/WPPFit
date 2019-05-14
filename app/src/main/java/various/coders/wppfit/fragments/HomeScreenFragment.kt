package various.coders.wppfit.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home_screen.*
import various.coders.wppfit.R
import various.coders.wppfit.model.AppViewModel
import various.coders.wppfit.model.calc.CaloriesCalc
import various.coders.wppfit.model.database.entities.Meal
import java.util.*


class HomeScreenFragment : Fragment() {
    private lateinit var viewModel: AppViewModel
    private var eSum = 0.0
    private var mSum = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(AppViewModel::class.java)

        viewModel.currentUser.observe(this, Observer {
            welcomeText.text = getString(R.string.home_welcome, it!!.firstName)
        })
        viewModel.meals.observe(this, Observer {
            mSum = it!!.sumByDouble { it.calories.toDouble() }
            updateText()
        })
        viewModel.exercises.observe(this, Observer {
            eSum = it!!.sumByDouble { it.calories.toDouble() }
            updateText()
        })

        //tests adding meals
        welcomeText.setOnClickListener {
            viewModel.insertMeal(
                Meal(
                    uid = 0,
                    user = viewModel.currentUser.value!!.uid,
                    time = Date(),
                    name = "Coś",
                    carbs = 33f,
                    calories = 33f,
                    protein = 33f,
                    fat = 33f,
                    weight = 33f
                )
            )
        }
    }

    private fun updateText() {
        subtitle.text = getString(R.string.home_subtitle, mSum)
        subtitle2.text = getString(R.string.home_subtitle2, eSum)
        goalText.text = getString(
            R.string.home_goal,
            mSum - eSum,
            CaloriesCalc.getCaloricIntake(viewModel.currentUser.value!!) + CaloriesCalc.getCaloricOffset(
                viewModel.currentUser.value!!,
                viewModel.targetDays,
                viewModel.targetWeight
            )
        )
    }
}
