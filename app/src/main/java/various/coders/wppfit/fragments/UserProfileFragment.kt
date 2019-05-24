package various.coders.wppfit.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.NumberPicker
import kotlinx.android.synthetic.main.fragment_user_profile.*

import various.coders.wppfit.R
import various.coders.wppfit.fragments.adapters.UserExerciseRecyclerViewAdapter
import various.coders.wppfit.fragments.adapters.UserMealRecyclerViewAdapter
import various.coders.wppfit.model.AppViewModel
import various.coders.wppfit.model.database.entities.Exercise
import various.coders.wppfit.model.database.entities.Meal

class UserProfileFragment : Fragment() {
    private lateinit var viewModel: AppViewModel

    private val mealList = mutableListOf<Meal>()
    private val exerciseList = mutableListOf<Exercise>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(AppViewModel::class.java)

        weightPicker.maxValue = 250
        weightPicker.minValue = 30
        viewModel.currentUser.observe(this, Observer {
            weightPicker.value = it!!.height
        })

        targetWeightPicker.maxValue = 250
        targetWeightPicker.minValue = 30
        targetWeightPicker.value =
            if (viewModel.targetWeight < 0) weightPicker.value else viewModel.targetWeight.toInt()

        targetTimePicker.maxValue = 90
        targetTimePicker.minValue = 1
        targetTimePicker.value = if (viewModel.targetDays < 0) 1 else viewModel.targetDays

        val weightFormatter = NumberPicker.Formatter {
            "$it kg"
        }
        weightPicker.setFormatter(weightFormatter)
        targetWeightPicker.setFormatter(weightFormatter)

        weightPicker.setOnValueChangedListener { _, _, newVal ->
            viewModel.updateUser(
                viewModel.currentUser.value!!.copy(weight = newVal)
            )
        }

        targetWeightPicker.setOnValueChangedListener { _, _, newVal ->
            viewModel.targetWeight = newVal.toDouble()
        }

        targetTimePicker.setOnValueChangedListener { _, _, newVal ->
            viewModel.targetDays = newVal
        }

        with(meals) {
            layoutManager = LinearLayoutManager(context)
            adapter = UserMealRecyclerViewAdapter(mealList)
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        }

        with(exercises) {
            layoutManager = LinearLayoutManager(context)
            adapter = UserExerciseRecyclerViewAdapter(exerciseList)
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        }

        viewModel.meals.observe(this, Observer {
            mealList.clear()
            mealList.addAll(it!!)
            meals.adapter.notifyDataSetChanged()
            meals.scheduleLayoutAnimation()
        })

        viewModel.exercises.observe(this, Observer {
            exerciseList.clear()
            exerciseList.addAll(it!!)
            exercises.adapter.notifyDataSetChanged()
            exercises.scheduleLayoutAnimation()
        })
    }
}
