package com.example.pines.home.detail

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pines.core.model.Pines
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class PinDetailViewModel : ViewModel() {

    private val _likes = MutableStateFlow(0)
    val likes: StateFlow<Int> = _likes.asStateFlow()

    private val _liked = MutableStateFlow(false)
    val liked: StateFlow<Boolean> = _liked.asStateFlow()

    fun loadPin(pin: Pines) {
        _likes.value = pin.likes
        _liked.value = pin.likedByUser
    }

    fun toggleLike() {
        if (_liked.value) {
            _liked.value = false

            if (_likes.value > 0) {
                _likes.value--
            }
        } else {
            _liked.value = true
            _likes.value++
        }
    }
}