package com.jintin.quickroute.extra

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.jintin.quickroute.R
import com.jintin.quickroute.data.Extra
import com.jintin.quickroute.databinding.FragmentEditExtraBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDialogFragment : DialogFragment() {

    companion object {
        const val EXTRA_EXTRA = "extra_extra"

        fun newInstance(
            extra: Extra? = null,
        ): EditDialogFragment {
            val fragment = EditDialogFragment()
            val args = Bundle()
            args.putParcelable(EXTRA_EXTRA, extra)
            fragment.arguments = args
            return fragment
        }
    }

    interface OnEditListener {
        fun onRemoveExtra(data: Extra)

        fun onUpdateExtra(oldData: Extra?, newData: Extra)
    }

    private val viewModel by viewModels<EditDialogViewModel>()
    private var extra: Extra? = null
    private var newCreate = false
    private var listener: OnEditListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnEditListener
            ?: throw RuntimeException("Should implement ${OnEditListener::class}")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extra = arguments?.getParcelable(EXTRA_EXTRA)
        newCreate = extra == null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireContext())
            .setView(createView())
            .setCancelable(false)
            .create()
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    private fun createView(): View {
        val binding = FragmentEditExtraBinding.inflate(requireActivity().layoutInflater)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            Extra.Type.values().map { it.label }
        )
        binding.type.setAdapter(adapter)
        extra?.let {
            binding.name.setText(it.name)
            binding.type.setText(it.type.label, false)
            binding.value.setText(it.value)
        }
        if (newCreate) {
            binding.apply.setText(R.string.txt_add)
            binding.remove.setText(android.R.string.cancel)
        } else {
            binding.apply.setText(R.string.txt_apply)
            binding.remove.setText(R.string.txt_remove)
        }

        binding.apply.setOnClickListener {
            val type = Extra.Type.getType(binding.type.text.toString())
            if (type == null) {
                Toast.makeText(requireContext(), "Type shouldn't be null", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val newExtra = Extra(
                binding.name.text.toString(),
                binding.value.text.toString(),
                type
            )
            listener?.onUpdateExtra(extra, newExtra)
            dismiss()
        }
        binding.remove.setOnClickListener {
            extra?.let {
                listener?.onRemoveExtra(it)
            }
            dismiss()
        }
        binding.name.addTextChangedListener {
            viewModel.setName(it.toString())
        }
        binding.type.addTextChangedListener {
            viewModel.setType(it.toString())
        }
        binding.value.addTextChangedListener {
            viewModel.setValue(it.toString())
        }
        viewModel.setExtra(extra)
        viewModel.validateLiveData.observe(this) {
            binding.apply.isEnabled = it
        }
        return binding.root
    }
}