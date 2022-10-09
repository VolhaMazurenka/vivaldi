package com.mazurenka.vivaldi.data.repository

import android.content.Context
import android.util.Log
import com.mazurenka.vivaldi.data.model.AudioModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class AudioRepository(private val moshi: Moshi) {

    fun getAudioList(context: Context): List<AudioModel> {
        return try {
            val inputStream = context.assets.open("media_list.json")
            val audioJson = inputStream.bufferedReader(Charsets.UTF_8).readText()
            val type = Types.newParameterizedType(List::class.java, AudioModel::class.java)
            val jsonAdapter: JsonAdapter<List<AudioModel>> = moshi.adapter(type)
            val audioList = jsonAdapter.fromJson(audioJson)
            Log.i(TAG, "list : ${audioList?.size}")
            audioList ?: emptyList()
        } catch (e: Exception) {
            Log.i(TAG, "list error: $e")
            return emptyList()
        }
    }

    companion object {
        private const val TAG = "PLAYER_APP"
    }
}