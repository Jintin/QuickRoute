package com.jintin.quickroute.select

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jintin.bindingextension.BindingActivity
import com.jintin.quickroute.base.bindEmptyView
import com.jintin.quickroute.data.AppInfo
import com.jintin.quickroute.databinding.ActivityAppBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppListActivity : BindingActivity<ActivityAppBinding>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AppListActivity::class.java))
        }
    }

    private val viewModel by viewModels<AppListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = AppListAdapter(packageManager, lifecycleScope, ::onSelect)
        adapter.bindEmptyView(binding.progressBar)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        viewModel.liveData.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun onSelect(info: AppInfo) {
        ActListActivity.start(this, info.name, info.packageName)
    }
}