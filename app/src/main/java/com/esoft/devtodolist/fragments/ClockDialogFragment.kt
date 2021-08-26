package com.esoft.devtodolist.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.esoft.devtodolist.databinding.ClockDialogFragmentBinding

class ClockDialogFragment : DialogFragment() {

    private lateinit var binding: ClockDialogFragmentBinding
    private var time: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ClockDialogFragmentBinding.inflate(inflater, container, false)
        onClick()
        return binding.root
    }

    private fun onClick() {
        binding.cancelTime.setOnClickListener {
            dismiss()
        }

        binding.clock.setOnTimeChangedListener { view, hourOfDay, minute ->
            time = "$hourOfDay:$minute"
        }

        binding.saveTime.setOnClickListener {
            if(time!!.isNotEmpty()) {
                val activity = activity
                if (activity != null && activity is SupportInterface) {
                    (activity as SupportInterface).getTime(time = time!!)
                }
            }
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