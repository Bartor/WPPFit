package various.coders.wppfit.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_add_meal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import various.coders.wppfit.R
import various.coders.wppfit.fragments.adapters.MealRecyclerViewAdapter
import various.coders.wppfit.model.AppViewModel
import various.coders.wppfit.model.web.FoodAPIClient
import various.coders.wppfit.model.web.schema.FoodMeasure
import various.coders.wppfit.model.web.schema.FoodResult

class AddMealFragment : Fragment(), OnMealInteractionInterface {
    private lateinit var viewModel: AppViewModel
    private val api = FoodAPIClient.getService()

    private lateinit var selectedFood: FoodMeasure

    private lateinit var apiId: String
    private lateinit var apiKey: String

    private val foodResults: MutableList<FoodMeasure> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        apiKey = getString(R.string.foodApiKey)
        apiId = getString(R.string.foodApiId)
        return inflater.inflate(R.layout.fragment_add_meal, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        unit.isEnabled = false

        with(recycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = MealRecyclerViewAdapter(foodResults, this@AddMealFragment)
        }

        searchButton.setOnClickListener {
            if (!foodName.text.isBlank()) {
                it.isEnabled = false

                api.foodQuery(foodName.text.toString().trim(), apiId, apiKey).enqueue(object : Callback<FoodResult> {
                    override fun onFailure(call: Call<FoodResult>?, t: Throwable?) {
                        Toast.makeText(activity, "There was an netowrk error", Toast.LENGTH_LONG).show()
                        it.isEnabled = true
                    }
                    override fun onResponse(call: Call<FoodResult>?, response: Response<FoodResult>?) {
                        if (response != null) {
                            foodResults.addAll(response.body().foods)
                            recycler.adapter.notifyDataSetChanged()
                        }
                        it.isEnabled = true
                    }
                })
            }
        }

        viewModel = ViewModelProviders.of(activity!!).get(AppViewModel::class.java)
    }

    override fun onMealInteraction(food: FoodMeasure) {
        //todo allow search for the net weight here
        selectedFood = food
        foodNumber.isEnabled = true
        unit.isEnabled = true
        unit.isEnabled = true
        addButton.isEnabled = true
    }
}

interface OnMealInteractionInterface {
    fun onMealInteraction(food: FoodMeasure)
}