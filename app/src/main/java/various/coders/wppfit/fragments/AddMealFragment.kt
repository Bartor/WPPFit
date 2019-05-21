package various.coders.wppfit.fragments

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.food_dialog.*
import kotlinx.android.synthetic.main.food_dialog.view.*
import kotlinx.android.synthetic.main.fragment_add_meal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import various.coders.wppfit.R
import various.coders.wppfit.fragments.adapters.MealRecyclerViewAdapter
import various.coders.wppfit.fragments.dialogs.MealDialog
import various.coders.wppfit.model.AppViewModel
import various.coders.wppfit.model.database.entities.Meal
import various.coders.wppfit.model.web.FoodAPIClient
import various.coders.wppfit.model.web.schema.*
import java.util.*

class AddMealFragment : Fragment(), OnMealInteractionInterface, MealDialogInterface {
    private lateinit var viewModel: AppViewModel
    private val api = FoodAPIClient.getService()

    private lateinit var apiId: String
    private lateinit var apiKey: String

    private lateinit var selectedFood: FoodMeasure

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
                            println(response.body())
                            foodResults.clear()
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
        selectedFood = food
        val mealCalc = MealDialog(activity, food)
        mealCalc.init(this)
        mealCalc.show()
    }

    override fun onMealAdd(food: NutrientsResult, weight: Float) {
        insertFood(food, weight)
    }

    private fun insertFood(food: NutrientsResult, weight: Float) {
        println("XD")
        viewModel.insertMeal(
            Meal(
                uid = 0,
                user = viewModel.currentUser.value!!.uid,
                time = Date(),
                name = selectedFood.food.label,
                carbs = food.nutrient.carbs?.quantity ?: 0f,
                protein = food.nutrient.protein?.quantity ?: 0f,
                fat = food.nutrient.fat?.quantity ?: 0f,
                calories = food.nutrient.calories?.quantity ?: 0f,
                weight = weight
            )
        )
        println("henl")
    }
}