package com.example.eventsincalendarforweek.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventsincalendarforweek.R
import com.example.eventsincalendarforweek.repository.Result

class CustomAdapterForRecyclerView(private val dataSet: List<Result>) : RecyclerView.Adapter<CustomAdapterForRecyclerView.ViewHolder>() { // to begin with just one string per line is passed

    /**
    * Provide a reference to the type of views that you are using
    * (custom ViewHolder).
    */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val labelTextView: TextView
        val startDateTextView: TextView
        val endDateTextView: TextView

        init {
            // get handles for each of the text views
            labelTextView = view.findViewById(R.id.eventLabel)
            startDateTextView = view.findViewById(R.id.startDate)
            endDateTextView = view.findViewById(R.id.endDate)
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.calendar_event_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.labelTextView.text = dataSet[position].label
        viewHolder.startDateTextView.text = dataSet[position].start
        viewHolder.endDateTextView.text = dataSet[position].end
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}