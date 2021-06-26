package com.jintin.quickroute.record

import android.content.Context
import android.os.Bundle
import android.view.View
import com.jintin.bindingextension.BindingBottomSheetDialogFragment
import com.jintin.quickroute.data.Record
import com.jintin.quickroute.databinding.FragmentRecordActionBinding

class RecordActionSheet : BindingBottomSheetDialogFragment<FragmentRecordActionBinding>() {

    interface OnActionListener {
        fun onDelete(data: Record)
    }

    companion object {
        const val EXTRA_RECORD = "extra_record"

        fun newInstance(data: Record): RecordActionSheet {
            val fragment = RecordActionSheet()
            fragment.arguments = Bundle().apply {
                putParcelable(EXTRA_RECORD, data)
            }
            return fragment
        }
    }

    var listener: OnActionListener? = null
    lateinit var data: Record

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = arguments?.getParcelable(EXTRA_RECORD)
            ?: throw RuntimeException("Should pass Record for RecordActionSheet select")
        binding.delete.setOnClickListener {
            listener?.onDelete(data)
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnActionListener
            ?: throw RuntimeException("Should implement OnActionListener")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}