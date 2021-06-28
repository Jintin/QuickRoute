package com.jintin.quickroute.action

import android.content.pm.PackageManager
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jintin.bindingextension.toBinding
import com.jintin.quickroute.base.ItemCallback
import com.jintin.quickroute.base.loadImage
import com.jintin.quickroute.data.Action
import com.jintin.quickroute.databinding.AdapterActionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ActionListAdapter(
    private val packageManager: PackageManager,
    private val lifecycleScope: CoroutineScope,
    private val onSelectListener: (Action) -> Unit,
    private val onActionListener: (Action) -> Unit
) : ListAdapter<Action, ActionListAdapter.ViewHolder>(ItemCallback<Action>()) {

    inner class ViewHolder(
        private val binding: AdapterActionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var job: Job? = null

        fun bind(data: Action) {
            job?.cancel()
            job = lifecycleScope.launch {
                packageManager.loadImage(binding.icon, data.packageName)
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