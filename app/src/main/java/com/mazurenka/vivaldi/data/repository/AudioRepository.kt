package com.mazurenka.vivaldi.data.repository

import com.mazurenka.vivaldi.data.model.AudioModel


class AudioRepository(
    private val jsonToAudioModelsMapper: JsonToAudioModelsMapper,
    private val jsonProvider: JsonProvider
) {

    fun getAudioList(): List<AudioModel> {
        return try {
            val audioJson = jsonProvider.getJson("media_list.json")
            jsonToAudioModelsMapper.map(audioJson)
        } catch (e: Exception) {
            return emptyList()
        }
    }
}