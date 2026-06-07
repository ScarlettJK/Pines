package com.example.pines.home.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pines.core.repositories.UserRepository
import com.example.pines.onboarding.personal.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow
import com.example.pines.core.repositories.BoardRepository


class AccountViewModel : ViewModel() {

    private val repository =
        UserRepository()

    private val boardRepository =
        BoardRepository()

    private val _user =
        MutableStateFlow<UserProfile?>(null)

    val user: StateFlow<UserProfile?> =
        _user


    private val _boardsCount =
        MutableStateFlow(0)

    val boardsCount =
        _boardsCount.asStateFlow()

    private val _pinsCount =
        MutableStateFlow(0)

    val pinsCount =
        _pinsCount.asStateFlow()


    fun loadUser(
        uid: String
    ) {

        viewModelScope.launch {

            _user.value =
                repository.getUserProfile(uid)
        }
    }

    fun loadStats() {

        viewModelScope.launch {

            _boardsCount.value =
                boardRepository.getBoardsCount()

            _pinsCount.value =
                boardRepository.getTotalPinsCount()
        }
    }

}