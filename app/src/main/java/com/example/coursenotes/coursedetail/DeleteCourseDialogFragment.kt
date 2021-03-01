package com.example.coursenotes.coursedetail

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.coursenotes.R

class DeleteCourseDialogFragment(private val viewModel: CourseDetailViewModel): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.delete_course_dialog_text)
                .setPositiveButton(R.string.delete_course_dialog_positive_text) { dialog, id -> viewModel.deleteCourse() }
                .setNegativeButton(R.string.delete_course_dialog_negative_text) { dialog, id -> dismiss() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}