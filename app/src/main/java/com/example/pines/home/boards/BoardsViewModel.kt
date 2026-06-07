package com.example.pines.home.boards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pines.core.repositories.BoardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BoardsViewModel : ViewModel() {

    private val repository =
        BoardRepository()

    private val _boards =
        MutableStateFlow<List<Board>>(emptyList())

    val boards: StateFlow<List<Board>> =
        _boards.asStateFlow()

    fun loadBoards() {
        viewModelScope.launch {
            _boards.value =
                repository.getBoards()
        }
    }

    fun createBoard(
        name: String
    ) {
        viewModelScope.launch {
            if (_boards.value.any {
                    it.name.equals(name, ignoreCase = true)
                }) {
                return@launch
            }

            repository.createBoard(name)
            loadBoards()

        }
    }

    fun deleteBoard(
        boardId: String
    ) {
        viewModelScope.launch {

            repository.deleteBoard(boardId)

            loadBoards()
        }
    }

}