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

import com.example.pines.core.repositories.LikeRepository
import com.example.pines.core.repositories.BoardRepository
import com.example.pines.home.boards.Board


class PinDetailViewModel : ViewModel() {

    private val _likes = MutableStateFlow(0)
    val likes: StateFlow<Int> = _likes.asStateFlow()

    private val _liked = MutableStateFlow(false)
    val liked: StateFlow<Boolean> = _liked.asStateFlow()

    private val repository =
        LikeRepository()

    private lateinit var pin: Pines


    /*
    private val boardRepository =
        BoardRepository()


    suspend fun getBoards(): List<Board> {
        return boardRepository.getBoards()
    }
*/

    fun loadPin(pin: Pines) {

        this.pin = pin
        _likes.value = pin.likes

        viewModelScope.launch {
            _liked.value = repository.isLiked(pin.id)
        }
    }

    fun toggleLike() {

        viewModelScope.launch {
            if (_liked.value) {

                repository.removeLike(pin.id)
                _liked.value = false

                if (_likes.value > 0) {
                    _likes.value--
                }
            } else {

                repository.saveLike(pin.id)
                _liked.value = true
                _likes.value++
            }
        }
    }



}