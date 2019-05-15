package various.coders.wppfit.fragments.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.food_item.view.*
import various.coders.wppfit.R
import various.coders.wppfit.fragments.OnMealInteractionInterface
import various.coders.wppfit.model.web.schema.FoodMeasure

class MealRecyclerViewAdapter(
    private val mValues: List<FoodMeasure>,
    private val mListener: OnMealInteractionInterface
) : RecyclerView.Adapter<MealRecyclerViewAdapter.ViewHolder>() {

    private val listener: View.OnClickListener

    init {
        listener = View.OnClickListener { v ->
            val item = v.tag as FoodMeasure
            mListener.onMealInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        holder.title.text = item.food.label.capitalize()
        holder.desc.text = "${item.food.nutrients.calories} kcal per ${item.measures[0].label.toLowerCase()}"

        with(holder.view) {
            tag = item
            setOnClickListener(listener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.image
        val title: TextView = view.foodItemName
        val desc: TextView = view.foodItemDesc
    }
}

