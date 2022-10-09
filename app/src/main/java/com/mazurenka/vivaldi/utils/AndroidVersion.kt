package com.mazurenka.vivaldi.utils

import android.os.Build


fun isO(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
}

fun isP(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
}

fun isQ(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}

fun isR(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
}