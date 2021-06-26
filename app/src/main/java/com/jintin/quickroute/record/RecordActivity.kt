package com.jintin.quickroute.record

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
import com.jintin.quickroute.data.Record
import com.jintin.quickroute.databinding.ActivityListBinding
import com.jintin.quickroute.select.AppSelectActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordActivity : BindingActivity<ActivityListBinding>(), RecordActionSheet.OnActionListener {

    companion object {
        fun bringToFront(context: Context) {
            context.startActivity(
                Intent(context, RecordActivity::class.java)
                    .addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
    }

    private val viewModel by viewModels<RecordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.txt_action_list)
        val adapter = RecordAdapter(
            packageManager,
            lifecycleScope,
            ::onSelect,
            ::onAction
        )
        adapter.bindEmptyView(binding.emptyHint)

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
            AppSelectActivity.start(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDelete(data: Record) {
        viewModel.delete(data)
    }

    private fun onSelect(data: Record) {
        try {
            startActivity(Intent().setClassName(data.packageName, data.actName))
        } catch (e: Exception) {
            Toast.makeText(this, R.string.txt_not_able_launch, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onAction(data: Record) {
        RecordActionSheet.newInstance(data)
            .show(supportFragmentManager, null)
    }

}