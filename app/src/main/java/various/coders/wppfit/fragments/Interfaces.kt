package various.coders.wppfit.fragments

import various.coders.wppfit.model.database.entities.User
import various.coders.wppfit.model.web.schema.FoodMeasure
import various.coders.wppfit.model.web.schema.NutrientsResult

interface OnListFragmentInteractionListener {
    fun onListFragmentInteraction(item: User)
}

interface OnMealInteractionInterface {
    fun onMealInteraction(food: FoodMeasure)
}

interface MealDialogInterface {
    fun onMealAdd(food: NutrientsResult, weight: Float)
}