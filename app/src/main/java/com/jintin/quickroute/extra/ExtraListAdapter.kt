package com.jintin.quickroute.extra

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jintin.bindingextension.toBinding
import com.jintin.quickroute.base.ItemCallback
import com.jintin.quickroute.data.Extra
import com.jintin.quickroute.databinding.AdapterExtraBinding

class ExtraListAdapter(private val onSelectListener: (Extra) -> Unit) :
    ListAdapter<Extra, ExtraListAdapter.ViewHolder>(ItemCallback<Extra>()) {

    inner class ViewHolder(private val binding: AdapterExtraBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: Extra) {
            binding.name.text = data.name
            binding.value.text = data.value
            binding.type.text = "${data.type.label}:"
            binding.root.setOnClickListener {
                onSelectListener.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.toBinding())

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}