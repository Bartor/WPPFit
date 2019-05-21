package various.coders.wppfit.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile_list.*
import various.coders.wppfit.EditProfileActivity
import various.coders.wppfit.MainActivity
import various.coders.wppfit.R
import various.coders.wppfit.fragments.adapters.ProfileRecyclerViewAdapter
import various.coders.wppfit.model.AppViewModel
import various.coders.wppfit.model.database.entities.User
import various.coders.wppfit.model.database.types.ActivityLevel
import java.util.*

class ChangeProfileFragment : Fragment(), OnListFragmentInteractionListener {
    private var columnCount = 2

    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(AppViewModel::class.java)

        viewModel.getAllUsers().observe(this, Observer {
            val l = it!!.toMutableList().apply {
                add(
                    User(
                        uid = -1,
                        firstName = getString(R.string.new_profile),
                        lastName = "",
                        gender = true,
                        age = Date(),
                        activity = ActivityLevel.NONE,
                        weight = 0,
                        height = 0
                    )
                )
            }
            with(list) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter =
                    ProfileRecyclerViewAdapter(l, this@ChangeProfileFragment)
            }
        })
    }

    override fun onListFragmentInteraction(item: User) {
        if (item.uid > 0) {
            (activity as MainActivity).updateModel(item.uid)
        } else {
            val intent = Intent(activity, EditProfileActivity::class.java)
            activity.startActivityForResult(intent, various.coders.wppfit.EDIT_PROFILE)
        }
    }
}