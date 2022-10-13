package com.mazurenka.vivaldi.data.repository

import com.mazurenka.vivaldi.data.model.AudioModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class JsonToAudioModelsMapper(private val moshi: Moshi) {

    fun map(audioJson: String): List<AudioModel> {
        val type = Types.newParameterizedType(List::class.java, AudioModel::class.java)
        val jsonAdapter: JsonAdapter<List<AudioModel>> = moshi.adapter(type)
        return jsonAdapter.fromJson(audioJson) ?: emptyList()
    }
}