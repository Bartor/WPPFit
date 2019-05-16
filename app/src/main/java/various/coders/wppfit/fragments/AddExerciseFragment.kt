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
import java.util.*

class AddExerciseFragment : Fragment() {
    private lateinit var viewModel: AppViewModel
    private lateinit var startDate: Date
    private lateinit var endDate: Date
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_exercise, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        startDateText.setOnClickListener {
            val picker = DatePickerDialog(activity).apply {
                setOnDateSetListener { _, year, month, dayOfMonth ->
                    this@AddExerciseFragment.updateStartDate(Date().also {
                        it.year = year
                        it.month = month
                        it.date = dayOfMonth
                    })
                }
            }
            picker.show()
        }
        endDateText.setOnClickListener {
            val picker = DatePickerDialog(
                activity,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    this@AddExerciseFragment.updateEndDate(Date().also {
                        it.year = year
                        it.month = month
                        it.date = dayOfMonth
                    })
                }, LocalDate.now().year,
                LocalDate.now().month.value,
                LocalDate.now().dayOfMonth

            )

            picker.show()
        }
        startTimeText.setOnClickListener {
            val picker = TimePickerDialog(
                activity,
                TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    updateStartTime(hour, minute)
                },
                LocalTime.now().hour,
                LocalTime.now().minute,
                true

            )
            picker.show()
        }
        endTimeText.setOnClickListener {
            val picker = TimePickerDialog(
                activity,
                TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    updateEndTime(hour, minute)
                },
                LocalTime.now().hour,
                LocalTime.now().minute,
                true

            )
            picker.show()
        }
        //get the activity's view model when activity is available
        viewModel = ViewModelProviders.of(activity!!).get(AppViewModel::class.java)

        exerciseSpinner.adapter =
            ArrayAdapter<ExerciseType>(activity, android.R.layout.simple_spinner_item, ExerciseType.values())

        addExcButton.setOnClickListener { apply() }
    }

    fun updateStartDate(date: Date) {
        //day is const in this piece of art library :^)
        date.hours = this.startDate.hours
        date.minutes = this.startDate.minutes
        this.startDate = date
    }

    fun updateEndDate(date: Date) {
        date.hours = this.endDate.hours
        date.minutes = this.endDate.minutes
        this.endDate = date
    }

    fun updateStartTime(hour: Int, minute: Int) {
        startDate.hours = hour
        startDate.minutes = minute
    }

    fun updateEndTime(hour: Int, minute: Int) {
        endDate.hours = hour
        endDate.minutes = minute
    }

    fun verify(): Boolean {
        return true
    }

    fun apply() {
        if (!verify())
            Toast.makeText(activity, "Fill up every box", Toast.LENGTH_LONG).show()
        else {
            var exercise = Exercise(
                uid = 0, //unique id - autoincrement, leave this at zero
                type = activitySpinner.selectedItem as ExerciseType,
                endTime = endDate,
                startTime = startDate,
                userRef = viewModel.currentUser.value!!
            )

            viewModel.insertExercise(
                exercise
            )
        }
    }
}
