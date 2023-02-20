package com.example.readerapp.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.repository.BooksRepository
import com.example.readerapp.models.MBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class State {
    Idle,
    GetUserBooks,
}

data class HomeState<T>(
    val state: State = State.Idle,
    val data: T? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(private val booksRepository: BooksRepository) : ViewModel() {
    private val _userBooks = mutableStateOf(HomeState<List<MBook?>>())
    val userBooks = _userBooks

    init {
        getUserBooks()
    }

    fun getUserBooks() {
        viewModelScope.launch {
            _userBooks.value = _userBooks.value.copy(state = State.GetUserBooks)
            val response = booksRepository.getUserBooks()
            _userBooks.value = _userBooks.value.copy(
                state = State.Idle,
                data = response
            )
        }
    }
}