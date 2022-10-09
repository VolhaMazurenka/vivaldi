package com.mazurenka.vivaldi.utils

import android.content.Context
import android.content.Intent


fun startForeground(context: Context, startServiceIntent: Intent) {
    if (isO()) {
        context.startForegroundService(startServiceIntent)
    } else {
        context.startService(startServiceIntent)
    }
}