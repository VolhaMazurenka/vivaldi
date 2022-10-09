package com.mazurenka.vivaldi.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mazurenka.vivaldi.data.model.AudioModel
import com.mazurenka.vivaldi.data.repository.AudioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayerViewModel(private val audioRepository: AudioRepository) : BaseViewModel() {

    val uiState get() = _uiState
    private val _uiState: MutableLiveData<UiState> = MutableLiveData(UiState(false, emptyList()))

    fun getAudioList(context: Context) {
        val state = _uiState.value?.copy(isLoading = true)
        state?.let { _uiState.postValue(state) }

        viewModelScope.launch(Dispatchers.IO) {
            delay(1500)
            val audioList = audioRepository.getAudioList(context)
            withContext(Dispatchers.Main) {
                val newState = _uiState.value?.copy(isLoading = false, audioList = audioList)
                newState?.let { _uiState.postValue(newState) }
            }
        }
    }

    data class UiState(
        val isLoading: Boolean,
        val audioList: List<AudioModel>
    )
}