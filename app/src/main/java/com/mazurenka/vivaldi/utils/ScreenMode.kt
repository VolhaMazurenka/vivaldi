package com.mazurenka.vivaldi.utils

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins


internal fun makeFullScreen(activity: Activity) {
    val decorView = activity.window.decorView
    @Suppress("DEPRECATION")
    when {
        isR() -> {
            activity.window.clearFlags(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            decorView.windowInsetsController?.setSystemBarsAppearance(0, 0)
            activity.window.setDecorFitsSystemWindows(false)
        }
        else -> {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}

internal fun makeFitSystemUI(view: View) {
    @Suppress("DEPRECATION")
    view.setOnApplyWindowInsetsListener { view, insets ->
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            when {
                isR() -> updateMargins(
                    top = insets.getInsets(WindowInsets.Type.statusBars()).top
                )
                else -> updateMargins(
                    top = insets.systemWindowInsetTop
                )
            }
        }
        insets
    }
}