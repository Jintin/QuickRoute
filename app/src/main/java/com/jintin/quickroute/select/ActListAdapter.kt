package com.jintin.quickroute.select

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jintin.bindingextension.toBinding
import com.jintin.quickroute.base.ItemCallback
import com.jintin.quickroute.data.Record
import com.jintin.quickroute.databinding.AdapterActBinding

class ActListAdapter(
    private val onSelectListener: (Record) -> Unit
) : ListAdapter<Record, ActListAdapter.ViewHolder>(ItemCallback<Record>()) {

    inner class ViewHolder(private val binding: AdapterActBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Record) {
            binding.name.text = data.actName
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