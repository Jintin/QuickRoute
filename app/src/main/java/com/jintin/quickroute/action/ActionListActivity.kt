package com.jintin.quickroute.action

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jintin.bindingextension.BindingActivity
import com.jintin.quickroute.R
import com.jintin.quickroute.base.bindEmptyView
import com.jintin.quickroute.data.Action
import com.jintin.quickroute.data.createIntent
import com.jintin.quickroute.databinding.ActivityActionBinding
import com.jintin.quickroute.extra.ExtraListActivity
import com.jintin.quickroute.select.AppListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActionListActivity : BindingActivity<ActivityActionBinding>(),
    SelectActionBottomSheet.OnActionListener {

    companion object {
        fun bringToFront(context: Context) {
            context.startActivity(
                Intent(context, ActionListActivity::class.java)
                    .addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
    }

    private val viewModel by viewModels<ActionListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.txt_action_list)
        val adapter = ActionListAdapter(
            packageManager,
            lifecycleScope,
            ::onSelect,
            ::onAction
        )
        adapter.bindEmptyView(binding.emptyView)

        binding.addAction.setOnClickListener {
            AppListActivity.start(this)
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        viewModel.liveData.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            AppListActivity.start(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDelete(data: Action) {
        viewModel.delete(data)
    }

    override fun onModify(data: Action) {
        ExtraListActivity.start(this, data, false)
    }

    private fun onSelect(data: Action) {
        try {
            startActivity(data.createIntent())

        } catch (e: Exception) {
            Toast.makeText(this, R.string.txt_not_able_launch, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onAction(data: Action) {
        SelectActionBottomSheet.newInstance(data)
            .show(supportFragmentManager, null)
    }

}