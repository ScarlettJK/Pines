package com.example.pines.home.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pines.core.model.Pines

import com.example.pines.core.ResponseService
import com.example.pines.core.network.PinesService
import com.example.pines.core.repositories.PinesRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedViewModel (
    private val service: PinesService = PinesRepository()

): ViewModel() {

    private val _pinesState = MutableStateFlow<ResponseService<List<Pines>>?>(null)
    val pinesState:StateFlow<ResponseService<List<Pines>>?> = _pinesState.asStateFlow()

    fun loadPines(limit: Int = 20) {
        viewModelScope.launch {
            _pinesState.value = ResponseService.Loading
            _pinesState.value = service.getPines(limit)
        }
    }

}
