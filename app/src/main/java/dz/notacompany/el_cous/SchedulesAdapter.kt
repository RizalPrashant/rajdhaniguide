package dz.notacompany.el_cous

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dz.notacompany.el_cous.databinding.ItemScheduleBinding

class SchedulesAdapter(private val context : Context, private var schedulesList: List<ScheduleItem>, private val onScheduleClick : (position : Int, textView : TextView) -> Unit) : RecyclerView.Adapter<SchedulesAdapter.SchedulesViewHolder>() {

    inner class SchedulesViewHolder(val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchedulesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemScheduleBinding.inflate(layoutInflater, parent, false)
        return SchedulesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SchedulesViewHolder, position: Int) {
        holder.binding.apply {

            itemTripCard.setOnClickListener {
                onScheduleClick(position, delaysTextView)
            }

            // Display schedule info in the layout item
            cousNumberTextView.text = schedulesList[position].transport
            departureTimeTextView.text = schedulesList[position].startPoint
            arrivalTimeTextView.text = schedulesList[position].endPoint
        }
    }

    override fun getItemCount(): Int {
        return  schedulesList.size
    }

}