package com.example.coursenotes.courselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coursenotes.database.Course
import com.example.coursenotes.databinding.ListItemCourseBinding

class CourseAdapter(val listener: CourseListener): ListAdapter<Course, CourseAdapter.ViewHolder>(CourseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = getItem(position)
        holder.bind(course, listener)
    }

    class ViewHolder private constructor(val binding: ListItemCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Course, listener: CourseListener) {
            binding.course = item
            binding.executePendingBindings()
            binding.listener = listener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCourseBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CourseDiffCallback: DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.courseId == newItem.courseId
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem
    }

}

class CourseListener(val clickListener: (courseId: Long) -> Unit) {
    fun onClick(course: Course) = clickListener(course.courseId)
}