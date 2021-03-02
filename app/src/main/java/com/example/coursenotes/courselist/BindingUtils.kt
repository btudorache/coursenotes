package com.example.coursenotes.courselist

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.coursenotes.R
import com.example.coursenotes.database.Course

@BindingAdapter("courseImage")
fun ImageView.setCourseImage(item: Course?) {
    item?.let {
        setImageResource(
            when (item.color) {
                0 -> R.drawable.ic_baseline_class_24_black
                1 -> R.drawable.ic_baseline_class_24_grey
                2 -> R.drawable.ic_baseline_class_24_red
                3 -> R.drawable.ic_baseline_class_24_green
                4 -> R.drawable.ic_baseline_class_24_blue
                // if color is 0
                else -> R.drawable.ic_baseline_class_24_black
            }
        )
    }
}
