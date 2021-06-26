package com.jintin.quickroute.record

import android.content.pm.PackageManager
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jintin.bindingextension.toBinding
import com.jintin.quickroute.base.ItemCallback
import com.jintin.quickroute.data.Record
import com.jintin.quickroute.databinding.AdapterRecordBinding
import kotlinx.coroutines.launch

class RecordAdapter(
    private val packageManager: PackageManager,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val onSelectListener: (Record) -> Unit,
    private val onActionListener: (Record) -> Unit
) : ListAdapter<Record, RecordAdapter.ViewHolder>(ItemCallback<Record>()) {

    inner class ViewHolder(
        private val binding: AdapterRecordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Record) {
            lifecycleScope.launch {
                binding.icon.setImageDrawable(data.getIcon(packageManager))
            }

            binding.name.text = data.name
            binding.act.text = data.actName
            binding.root.setOnClickListener {
                onSelectListener.invoke(data)
            }
            binding.root.setOnLongClickListener {
                onActionListener.invoke(data)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.toBinding())


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}