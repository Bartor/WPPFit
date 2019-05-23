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
import various.coders.wppfit.model.NutritionInfo
import various.coders.wppfit.model.calc.CaloriesCalc
import various.coders.wppfit.model.database.entities.Meal
import various.coders.wppfit.support.SynchronizedCaller
import java.lang.Math.floor
import java.util.*


class HomeScreenFragment : Fragment() {
    private lateinit var viewModel: AppViewModel

    private var intake = 0.0
    private lateinit var nutritionInfo: NutritionInfo
    private var eSum = 0.0
    private var mSum = 0.0
    private var carbs = 0.0
    private var fat = 0.0
    private var protein = 0.0

    private val sync = SynchronizedCaller(3, ::updateText)

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
            intake = CaloriesCalc.getCaloricIntake(it) - CaloriesCalc.getCaloricOffset(
                it,
                viewModel.targetDays,
                viewModel.targetWeight
            )
            nutritionInfo = CaloriesCalc.getMacroNutrientsRatio(it, viewModel.targetDays, viewModel.targetWeight)
            sync.call(0)
        })
        viewModel.meals.observe(this, Observer {
            mSum = it!!.sumByDouble { it.calories.toDouble() }
            carbs = it.sumByDouble { it.carbs.toDouble() }
            fat = it.sumByDouble { it.fat.toDouble() }
            protein = it.sumByDouble { it.protein.toDouble() }
            sync.call(1)
        })
        viewModel.exercises.observe(this, Observer {
            eSum = it!!.sumByDouble { it.calories.toDouble() }
            sync.call(2)
        })
    }

    private fun updateText() {
        subtitle.text = getString(R.string.home_subtitle, mSum)
        subtitle2.text = getString(R.string.home_subtitle2, eSum)
        goalText.text = getString(R.string.home_goal, mSum + eSum, intake)

        caloriesProgress.max = intake.toInt()
        caloriesProgress.progress = (mSum + eSum).toInt()

        proteinText.text = getString(R.string.home_goal_protein, protein, nutritionInfo.protein)
        proteinProgess.max = nutritionInfo.protein.toInt()
        proteinProgess.progress = protein.toInt()

        fatText.text = getString(R.string.home_goal_fat, fat, nutritionInfo.fat)
        fatProgress.max = nutritionInfo.fat.toInt()
        fatProgress.progress = fat.toInt()

        carbsText.text = getString(R.string.home_goal_carbs, carbs, nutritionInfo.carbs)
        carbsProgress.max = nutritionInfo.carbs.toInt()
        carbsProgress.progress = carbs.toInt()
    }
}
