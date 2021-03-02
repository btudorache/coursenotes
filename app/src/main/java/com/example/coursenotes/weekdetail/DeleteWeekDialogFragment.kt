package com.example.coursenotes.weekdetail

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.coursenotes.R


class DeleteWeekDialogFragment(private val viewModel: WeekDetailViewModel): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.delete_week_dialog_text)
                .setPositiveButton(R.string.delete_dialog_positive_text) { dialog, id -> viewModel.deleteWeek() }
                .setNegativeButton(R.string.delete_dialog_negative_text) { dialog, id -> dismiss() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}