package various.coders.wppfit.fragments.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import various.coders.wppfit.R

import kotlinx.android.synthetic.main.profile_item.view.*
import various.coders.wppfit.fragments.OnListFragmentInteractionListener
import various.coders.wppfit.model.database.entities.User

class ProfileRecyclerViewAdapter(
    private val mValues: List<User>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as User
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        holder.mIdView.text = item.firstName
        holder.mContentView.text = item.lastName

        if (item.uid < 0)  {
            holder.mImage.setImageResource(R.drawable.new_profile)
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        val mImage: ImageView = mView.image
    }
}
