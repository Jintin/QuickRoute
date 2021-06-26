package com.jintin.quickroute.select

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jintin.bindingextension.BindingActivity
import com.jintin.quickroute.base.bindEmptyView
import com.jintin.quickroute.data.Record
import com.jintin.quickroute.databinding.ActivityListLoadingBinding
import com.jintin.quickroute.record.RecordActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActSelectActivity : BindingActivity<ActivityListLoadingBinding>() {

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PACKAGE = "extra_package"

        fun start(context: Context, appName: String, packageName: String) {
            context.startActivity(
                Intent(context, ActSelectActivity::class.java)
                    .putExtra(EXTRA_NAME, appName)
                    .putExtra(EXTRA_PACKAGE, packageName)
            )
        }
    }

    private val viewModel by viewModels<ActSelectViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appName =
            intent.getStringExtra(EXTRA_NAME) ?: throw RuntimeException("")
        val packageName =
            intent.getStringExtra(EXTRA_PACKAGE) ?: throw RuntimeException("")

        val adapter = ActListAdapter(::onSelect)
        adapter.bindEmptyView(binding.progressBar)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter


        viewModel.getList(appName, packageName)
        viewModel.liveData.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun onSelect(record: Record) {
        viewModel.add(record)
        RecordActivity.bringToFront(this)
    }
}