package various.coders.wppfit.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.food_dialog.*
import kotlinx.android.synthetic.main.food_dialog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import various.coders.wppfit.R
import various.coders.wppfit.fragments.MealDialogInterface
import various.coders.wppfit.model.web.FoodAPIClient
import various.coders.wppfit.model.web.schema.*

class MealDialog(context: Context?, private val selectedFood: FoodMeasure) : AlertDialog(context) {
    private val api = FoodAPIClient.getService()

    private lateinit var listener: MealDialogInterface

    private var weight = 0f

    private lateinit var apiKey: String
    private lateinit var apiId: String

    private var calculatedFood: NutrientsResult? = null

    public fun init(context: MealDialogInterface) {
        listener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val builder = Builder(context)
        builder.setMessage(R.string.meal_calc)
        val layout = layoutInflater.inflate(R.layout.food_dialog, null)

        apiKey = context.getString(R.string.foodApiKey)
        apiId = context.getString(R.string.foodApiId)

        layout.apply {
            foodCalories.text = "%.1f".format(selectedFood.food.nutrients.calories)
            foodCarbs.text = "%.1f".format(selectedFood.food.nutrients.carbs)
            foodFat.text = "%.1f".format(selectedFood.food.nutrients.fat)
            foodProtein.text = "%.1f".format(selectedFood.food.nutrients.proteins)

            unit.adapter = ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                selectedFood.measures.map { it.label })

            calculateButton.setOnClickListener {
                weight = foodNumber.text.toString().toFloatOrNull() ?: 1f
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
                        Toast.makeText(context, "There was an network error", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<NutrientsResult>?, response: Response<NutrientsResult>?) {
                        if (response != null) {
                            calculatedFood = response.body()
                            yourFoodCalories.text = "%.1f".format(calculatedFood?.nutrient?.calories?.quantity ?: 0f)
                            yourFoodCarbs.text = "%.1f".format(calculatedFood?.nutrient?.carbs?.quantity ?: 0f)
                            yourFoodProtein.text = "%.1f".format(calculatedFood?.nutrient?.protein?.quantity ?: 0f)
                            yourFoodFat.text = "%.1f".format(calculatedFood?.nutrient?.fat?.quantity ?: 0f)
                        }
                    }
                })
            }
        }
        builder.setPositiveButton("Add") { _, _ ->
            if (calculatedFood != null) listener.onMealAdd(
                calculatedFood!!, weight
            )
        }

        builder.setView(layout)
        builder.create().show()
    }
}