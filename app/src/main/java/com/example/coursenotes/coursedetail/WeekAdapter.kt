package com.example.coursenotes.coursedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coursenotes.database.Week
import com.example.coursenotes.databinding.ListItemWeekBinding

class WeekAdapter(val listener: WeekListener): ListAdapter<Week, WeekAdapter.ViewHolder>(WeekDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val week = getItem(position)
        holder.bind(week, listener)
    }

    class ViewHolder private constructor(val binding: ListItemWeekBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Week, listener: WeekListener) {
            binding.week = item
            binding.executePendingBindings()
            binding.listener = listener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemWeekBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class WeekDiffCallback: DiffUtil.ItemCallback<Week>() {
    override fun areItemsTheSame(oldItem: Week, newItem: Week): Boolean {
        return oldItem.weekId == newItem.weekId
    }

    override fun areContentsTheSame(oldItem: Week, newItem: Week): Boolean {
        return oldItem == newItem
    }

}

class WeekListener(val clickListener: (weekId: Long) -> Unit) {
    fun onClick(week: Week) = clickListener(week.weekId)
}