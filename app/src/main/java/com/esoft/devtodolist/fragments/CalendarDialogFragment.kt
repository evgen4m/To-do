package com.esoft.devtodolist.fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.esoft.devtodolist.databinding.CalendarDialogFragmentBinding


class CalendarDialogFragment: DialogFragment() {

    private lateinit var binding: CalendarDialogFragmentBinding
    private var date: String? = null

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CalendarDialogFragmentBinding.inflate(inflater, container, false)
        onClick()
        return binding.root
    }

    private fun onClick() {

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = "$dayOfMonth.${month + 1}.$year"
        }

        binding.saveDate.setOnClickListener {
            if(date!!.isNotEmpty()) {
                val activity = activity
                if (activity != null && activity is SupportInterface) {
                    (activity as SupportInterface).getDate(date = date!!)
                }
            }
            dismiss()
        }

        binding.cancelDate.setOnClickListener {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }
}