package com.example.pines.home.boards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pines.core.model.Pines
import com.example.pines.core.repositories.PinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BoardDetailViewModel : ViewModel() {

    private val repository =
        PinRepository()

    private val _pins =
        MutableStateFlow<List<Pines>>(emptyList())

    val pins: StateFlow<List<Pines>> =
        _pins.asStateFlow()

    fun loadPins(
        boardId: String
    ) {

        viewModelScope.launch {

            _pins.value =
                repository.getPinsFromBoard(boardId)
        }
    }
}