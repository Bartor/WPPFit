package various.coders.wppfit.fragments


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_add_exercise.*

import various.coders.wppfit.model.AppViewModel
import various.coders.wppfit.model.calc.CaloriesCalc
import various.coders.wppfit.model.database.entities.Exercise
import various.coders.wppfit.model.database.types.ExerciseType
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.*
import android.widget.NumberPicker
import various.coders.wppfit.R

private const val MINUTES_STEP = 5

class AddExerciseFragment : Fragment() {
    private lateinit var viewModel: AppViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_exercise, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //get the activity's view model when activity is available
        viewModel = ViewModelProviders.of(activity!!).get(AppViewModel::class.java)

        exerciseSpinner.adapter =
            ArrayAdapter<ExerciseType>(activity, android.R.layout.simple_spinner_dropdown_item, ExerciseType.values())

        addExcButton.setOnClickListener { apply() }

        minutesPicker.minValue = 0
        minutesPicker.maxValue = 50

        val formatter = NumberPicker.Formatter { value ->
            val temp = value * MINUTES_STEP
            "" + temp
        }
        minutesPicker.setFormatter(formatter)
    }

    private fun apply() {
        val endDate: Date = Date.from(
            LocalTime.now().atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()
        )
        val minutes = minutesPicker.value * MINUTES_STEP
        val startDate = Date(System.currentTimeMillis() - minutes * 60 * 1000)
        val exercise = Exercise(
            uid = 0, //unique id - autoincrement, leave this at zero
            type = exerciseSpinner.selectedItem as ExerciseType,
            endTime = endDate,
            startTime = startDate,
            user = viewModel.currentUser.value!!.uid,
            calories = CaloriesCalc.getCalLossFromActivity(
                endDate,
                startDate,
                exerciseSpinner.selectedItem as ExerciseType,
                viewModel.currentUser.value!!
            )
        )

        viewModel.insertExercise(exercise)
        Toast.makeText(activity, getString(R.string.exercise_added), Toast.LENGTH_LONG).show()
        minutesPicker.value = 0
    }
}
