package com.jintin.quickroute.data

import android.content.Intent

fun Action.createIntent(): Intent {
    return Intent().setClassName(packageName, actName).also { intent ->
        extras.map {
            it.fillExtra(intent)
        }
    }
}

fun Extra.fillExtra(intent: Intent) {
    when (this.type) {
        Extra.Type.STRING -> intent.putExtra(name, value)
        Extra.Type.INT -> intent.putExtra(name, value.toInt())
        Extra.Type.FLOAT -> intent.putExtra(name, value.toFloat())
        Extra.Type.BYTE -> intent.putExtra(name, value.toByte())
        Extra.Type.CHAR -> intent.putExtra(name, value.single())
        Extra.Type.LONG -> intent.putExtra(name, value.toLong())
        Extra.Type.SHORT -> intent.putExtra(name, value.toShort())
        Extra.Type.DOUBLE -> intent.putExtra(name, value.toDouble())
        Extra.Type.BOOLEAN -> intent.putExtra(name, value.toBoolean())
    }
}