package various.coders.wppfit.fragments


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_add_exercise.*

import various.coders.wppfit.R
import various.coders.wppfit.model.AppViewModel
import various.coders.wppfit.model.calc.CaloriesCalc
import various.coders.wppfit.model.database.entities.Exercise
import various.coders.wppfit.model.database.types.ActivityLevel
import various.coders.wppfit.model.database.types.ExerciseType
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

class AddExerciseFragment : Fragment() {
    private lateinit var viewModel: AppViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_exercise, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState);



        //get the activity's view model when activity is available
        viewModel = ViewModelProviders.of(activity!!).get(AppViewModel::class.java)

        exerciseSpinner.adapter =
            ArrayAdapter<ExerciseType>(activity, android.R.layout.simple_spinner_item, ExerciseType.values())

        addExcButton.setOnClickListener { apply()  }
    }




    private fun apply() {


        val endDate:Date = Date.from(
            LocalTime.now().atDate(LocalDate.now()).
                atZone(ZoneId.systemDefault()).toInstant() //TODO editText -> spinner
        )
        val minutes = minutesPicker.text.toString().toInt()
        val startDate = Date(System.currentTimeMillis() - minutes * 60 * 1000)
        val exercise = Exercise(
            uid = 0, //unique id - autoincrement, leave this at zero
            type = activitySpinner.selectedItem as ExerciseType,
            endTime = endDate,
            startTime = startDate,
            user = viewModel.currentUser.value!!.uid,
            calories = CaloriesCalc.getCalLossFromActivity(
                endDate,
                startDate,
                activitySpinner.selectedItem as ExerciseType,
                viewModel.currentUser.value!!
            )
        )

        viewModel.insertExercise(
            exercise
        )
    }

}
