package com.jintin.quickroute

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.provider.Settings
import android.service.quicksettings.TileService
import android.widget.Toast

class DevRouteService : TileService() {

    override fun onClick() {
        super.onClick()
        val adb = Settings.Secure.getInt(
            this.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
            0
        )
        if (adb == 1) {
            startActivityAndCollapse(getIntent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))
        } else {
            startActivityAndCollapse(getIntent(Settings.ACTION_SETTINGS))
            Toast.makeText(
                applicationContext,
                R.string.dev_not_enable_msg,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getIntent(action: String): Intent {
        return Intent(action).apply {
            flags = FLAG_ACTIVITY_NEW_TASK
        }
    }
}