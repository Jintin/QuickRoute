package com.jintin.quickroute.extra

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jintin.bindingextension.BindingActivity
import com.jintin.quickroute.R
import com.jintin.quickroute.action.ActionListActivity
import com.jintin.quickroute.base.bindEmptyView
import com.jintin.quickroute.data.Action
import com.jintin.quickroute.data.Extra
import com.jintin.quickroute.databinding.ActivityExtraBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExtraListActivity : BindingActivity<ActivityExtraBinding>(),
    EditDialogFragment.OnEditListener {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_NEW = "extra_new"

        fun start(context: Context, action: Action, newCreate: Boolean) {
            context.startActivity(
                Intent(context, ExtraListActivity::class.java)
                    .putExtra(EXTRA_DATA, action)
                    .putExtra(EXTRA_NEW, newCreate)
            )
        }
    }

    private val viewModel by viewModels<ExtraListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.setData(
                intent.getParcelableExtra(EXTRA_DATA)
                    ?: throw RuntimeException("Should pass ${Action::class} to edit"),
                intent.getBooleanExtra(EXTRA_NEW, false)
            )
        }

        val adapter = ExtraListAdapter(::onSelect)
        adapter.bindEmptyView(binding.emptyView)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        viewModel.liveData.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.extra, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> {
                EditDialogFragment.newInstance().show(supportFragmentManager, null)
                return true
            }
            R.id.menu_done -> {
                onApply()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onSelect(data: Extra) {
        EditDialogFragment.newInstance(data).show(supportFragmentManager, null)
    }

    private fun onApply() {
        viewModel.applyAction {
            ActionListActivity.bringToFront(this)
        }
    }

    override fun onRemoveExtra(data: Extra) {
        viewModel.remove(data)
    }

    override fun onUpdateExtra(oldData: Extra?, newData: Extra) {
        if (oldData == null) {
            viewModel.add(newData)
        } else {
            viewModel.update(oldData, newData)
        }
    }
}