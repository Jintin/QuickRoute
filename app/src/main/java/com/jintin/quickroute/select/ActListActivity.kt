package com.jintin.quickroute.select

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jintin.bindingextension.BindingActivity
import com.jintin.quickroute.base.bindEmptyView
import com.jintin.quickroute.data.Action
import com.jintin.quickroute.databinding.ActivityActBinding
import com.jintin.quickroute.extra.ExtraListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActListActivity : BindingActivity<ActivityActBinding>() {

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PACKAGE = "extra_package"

        fun start(context: Context, appName: String, packageName: String) {
            context.startActivity(
                Intent(context, ActListActivity::class.java)
                    .putExtra(EXTRA_NAME, appName)
                    .putExtra(EXTRA_PACKAGE, packageName)
            )
        }
    }

    private val viewModel by viewModels<ActListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appName =
            intent.getStringExtra(EXTRA_NAME) ?: throw RuntimeException("")
        val packageName =
            intent.getStringExtra(EXTRA_PACKAGE) ?: throw RuntimeException("")

        val adapter = ActListAdapter(::onSelect)
        adapter.bindEmptyView(binding.emptyView)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        viewModel.getList(appName, packageName)
        viewModel.liveData.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun onSelect(action: Action) {
        ExtraListActivity.start(this, action, true)
    }
}