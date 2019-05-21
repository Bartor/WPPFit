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
import various.coders.wppfit.model.AppViewModel
import various.coders.wppfit.model.database.entities.Meal
import various.coders.wppfit.model.web.FoodAPIClient
import various.coders.wppfit.model.web.schema.*
import java.util.*

class AddMealFragment : Fragment(), OnMealInteractionInterface {
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
        mealCalc(food)
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

    private fun mealCalc(selectedFood: FoodMeasure) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.meal_calc))
        val layout = layoutInflater.inflate(R.layout.food_dialog, null)
        layout.apply {
            foodCalories.text = "%.1f".format(selectedFood.food.nutrients.calories)
            foodCarbs.text = "%.1f".format(selectedFood.food.nutrients.carbs)
            foodFat.text = "%.1f".format(selectedFood.food.nutrients.fat)
            foodProtein.text = "%.1f".format(selectedFood.food.nutrients.proteins)

            unit.adapter = ArrayAdapter<String>(
                activity,
                android.R.layout.simple_spinner_dropdown_item,
                selectedFood.measures.map { it.label })

            calculateButton.setOnClickListener {
                api.nutrientsQuery(
                    apiId,
                    apiKey,
                    NutrientsBody(
                        listOf(
                            Ingredient(
                                foodId = selectedFood.food.foodId,
                                measureUri = selectedFood.measures.filter { it.label == unit.selectedItem }[0].uri,
                                quantity = foodNumber.text.toString().toFloatOrNull() ?: 1f
                            )
                        )
                    )
                ).enqueue(object : Callback<NutrientsResult> {
                    override fun onFailure(call: Call<NutrientsResult>?, t: Throwable?) {
                        Toast.makeText(activity, "There was an network error", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<NutrientsResult>?, response: Response<NutrientsResult>?) {
                        if (response != null) {
                            val food = response.body()
                            yourFoodCalories.text = "%.1f".format(food?.nutrient?.calories?.quantity ?: 0f)
                            yourFoodCarbs.text = "%.1f".format(food?.nutrient?.carbs?.quantity ?: 0f)
                            yourFoodProtein.text = "%.1f".format(food?.nutrient?.protein?.quantity ?: 0f)
                            yourFoodFat.text = "%.1f".format(food?.nutrient?.fat?.quantity ?: 0f)
                        }
                    }
                })
            }
        }
        builder.setView(layout)
        //this is bad, but the dialogs are stateless so
        builder.setPositiveButton("Add") { _, _ ->
            //todo fix inserting this
            insertFood(
                NutrientsResult(
                    nutrient = NutrientResult(
                        calories = Nutrient(
                            label = "calories",
                            quantity = yourFoodCalories.text.toString().toFloat(),
                            unit = "g"
                        ),
                        fat = Nutrient(label = "fat", quantity = yourFoodFat.text.toString().toFloat(), unit = "g"),
                        carbs = Nutrient(
                            label = "carbs",
                            quantity = yourFoodCarbs.text.toString().toFloat(),
                            unit = "g"
                        ),
                        protein = Nutrient(
                            label = "protein",
                            quantity = yourFoodProtein.text.toString().toFloat(),
                            unit = "g"
                        )
                    )
                ), weight = foodNumber.text.toString().toFloatOrNull() ?: 1f
            )
        }
        builder.create().show()
    }
}

interface OnMealInteractionInterface {
    fun onMealInteraction(food: FoodMeasure)
}