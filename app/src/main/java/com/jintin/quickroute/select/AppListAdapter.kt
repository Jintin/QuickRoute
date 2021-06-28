package com.jintin.quickroute.select

import android.content.pm.PackageManager
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jintin.bindingextension.toBinding
import com.jintin.quickroute.base.ItemCallback
import com.jintin.quickroute.base.loadImage
import com.jintin.quickroute.data.AppInfo
import com.jintin.quickroute.databinding.AdapterAppBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AppListAdapter(
    private val packageManager: PackageManager,
    private val lifecycleScope: CoroutineScope,
    private val onSelectListener: (AppInfo) -> Unit
) : ListAdapter<AppInfo, AppListAdapter.ViewHolder>(ItemCallback<AppInfo>()) {

    inner class ViewHolder(private val binding: AdapterAppBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var job: Job? = null

        fun bind(data: AppInfo) {
            job?.cancel()
            job = lifecycleScope.launch {
                packageManager.loadImage(binding.icon, data.packageName)
            }
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