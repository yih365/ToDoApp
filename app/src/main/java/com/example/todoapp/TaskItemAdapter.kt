package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * A bridge that tells the recyclerview how to display data we give it
 */
class TaskItemAdapter(val listOfItems: MutableList<String>, val listOfDates: MutableList<String>,
                      val longClickListener : OnLongClickListener):
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        // store references in our layout view
        val textView: TextView
        val textView2: TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)
            textView2 = itemView.findViewById(android.R.id.text2)

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val taskItemView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)

        return ViewHolder(taskItemView)
    }

    // populates viewholder with data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listOfItems.get(position)
        val dateItem = listOfDates.get(position)

        holder.textView.text = item
        holder.textView2.text = dateItem
    }
}