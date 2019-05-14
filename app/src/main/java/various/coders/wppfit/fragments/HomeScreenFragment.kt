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
import various.coders.wppfit.model.database.entities.Meal
import java.util.*


class HomeScreenFragment : Fragment() {
    private lateinit var viewModel: AppViewModel

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
            println(it)
            welcomeText.text = getString(R.string.home_welcome, it!!.firstName)
        })
        viewModel.meals.observe(this, Observer {
            println(it)
            subtitle.text = getString(R.string.home_subtitle, it!!.sumByDouble { it.calories.toDouble() })
        })
        viewModel.exercises.observe(this, Observer {
            println(it)
            subtitle2.text = getString(R.string.home_subtitle2, it!!.sumByDouble { it.calories.toDouble() })
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
}
