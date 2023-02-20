package com.example.readerapp.ui.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.data.Resource
import com.example.readerapp.models.Books
import com.example.readerapp.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class State {
    Idle,
    GetAllBooks,
}

data class SearchState<T>(
    val state: State = State.Idle,
    val resource: Resource<T>? = null
)

@HiltViewModel
class SearchViewModel @Inject constructor(private val booksRepository: BooksRepository) :
    ViewModel() {
    private val _allBooks = mutableStateOf(SearchState<Books>())
    val allBooks = _allBooks

    fun getAllBooks(searchQuery: String) {
        viewModelScope.launch {
            _allBooks.value = _allBooks.value.copy(state = State.GetAllBooks)
            val response = booksRepository.queryBooks(query = searchQuery)
            _allBooks.value = _allBooks.value.copy(
                state = State.Idle,
                resource = response
            )
        }
    }
}