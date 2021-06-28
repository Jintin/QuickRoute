package com.jintin.quickroute.action

import android.content.Context
import android.os.Bundle
import android.view.View
import com.jintin.bindingextension.BindingBottomSheetDialogFragment
import com.jintin.quickroute.data.Action
import com.jintin.quickroute.databinding.FragmentActionSheetBinding

class SelectActionBottomSheet : BindingBottomSheetDialogFragment<FragmentActionSheetBinding>() {

    interface OnActionListener {
        fun onDelete(data: Action)

        fun onModify(data: Action)
    }

    companion object {
        const val EXTRA_ACTION = "extra_action"

        fun newInstance(data: Action): SelectActionBottomSheet {
            val fragment = SelectActionBottomSheet()
            fragment.arguments = Bundle().apply {
                putParcelable(EXTRA_ACTION, data)
            }
            return fragment
        }
    }

    private var listener: OnActionListener? = null
    private lateinit var data: Action

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = arguments?.getParcelable(EXTRA_ACTION)
            ?: throw RuntimeException("Should pass ${Action::class} for ${this::class} select")
        binding.delete.setOnClickListener {
            listener?.onDelete(data)
            dismiss()
        }
        binding.modify.setOnClickListener {
            listener?.onModify(data)
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnActionListener
            ?: throw RuntimeException("Should implement ${OnActionListener::class}")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}