package various.coders.wppfit.fragments


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import various.coders.wppfit.R
import various.coders.wppfit.model.AppViewModel
import various.coders.wppfit.model.database.entities.Exercise
import various.coders.wppfit.model.database.types.ExerciseType
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
        super.onActivityCreated(savedInstanceState)

        //get the activity's view model when activity is available
        viewModel = ViewModelProviders.of(activity!!).get(AppViewModel::class.java)

        //example insertion
        viewModel.insertExercise(
            Exercise(
                uid = 0, //unique id - autoincrement, leave this at zero
                user = viewModel.currentUser.value!!.uid, //current user unique id, from the viewModel
                type = ExerciseType.OTHER,
                calories = 0,
                endTime = Date(),
                startTime = Date()
            )
        )
    }
}
