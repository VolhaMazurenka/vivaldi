package com.mazurenka.vivaldi.data.repository

import android.content.Context
import android.util.Log
import com.mazurenka.vivaldi.data.model.AudioModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class JsonProvider(private val context: Context) {

    fun getJson(fileName: String): String {
        return try {
            val inputStream = context.assets.open(fileName)
            inputStream.bufferedReader(Charsets.UTF_8).readText()
        } catch (e: Exception) {
            return ""
        }
    }
}