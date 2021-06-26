package com.jintin.quickroute.select

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jintin.bindingextension.toBinding
import com.jintin.quickroute.base.ItemCallback
import com.jintin.quickroute.data.AppInfo
import com.jintin.quickroute.databinding.AdapterAppBinding

class AppListAdapter(
    private val onSelectListener: (AppInfo) -> Unit
) : ListAdapter<AppInfo, AppListAdapter.ViewHolder>(ItemCallback<AppInfo>()) {

    inner class ViewHolder(private val binding: AdapterAppBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: AppInfo) {
            binding.icon.setImageDrawable(data.icon)
            binding.name.text = data.name
            binding.root.setOnClickListener { onSelectListener.invoke(data) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.toBinding())

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}