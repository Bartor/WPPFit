package various.coders.wppfit.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import various.coders.wppfit.R
import various.coders.wppfit.model.AppViewModel
import various.coders.wppfit.model.web.FoodAPIClient
import various.coders.wppfit.model.web.FoodService
import various.coders.wppfit.model.web.POJO.FoodResult

class AddMealFragment : Fragment() {
    private lateinit var viewModel: AppViewModel
    private val api = FoodAPIClient.getService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_meal, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(AppViewModel::class.java)

        //test query
        api.foodQuery("green apple", "my id here lol", "my key here lol").enqueue(object :
            Callback<FoodResult> {
            override fun onFailure(call: Call<FoodResult>?, t: Throwable?) {
                println(t?.localizedMessage)
            }

            override fun onResponse(call: Call<FoodResult>?, response: Response<FoodResult>?) {
                println(response!!.body())
            }
        })
    }
}
