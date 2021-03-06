package various.coders.wppfit.fragments.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_basic.view.*
import various.coders.wppfit.R
import various.coders.wppfit.model.database.entities.Meal

class UserMealRecyclerViewAdapter(
    private val values: List<Meal>
) : RecyclerView.Adapter<UserMealRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_basic, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.title.text = item.name
        holder.subtitle.text = "${"%.1f".format(item.calories)} kcal"
        holder.additional.text = "${"%.1f".format(item.weight)} g"

        holder.view.tag = item
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.titleText
        val subtitle: TextView = view.descriptionText
        val additional: TextView = view.additionalText
    }
}