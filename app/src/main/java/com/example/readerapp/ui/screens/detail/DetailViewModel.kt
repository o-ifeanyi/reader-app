package com.example.readerapp.ui.screens.detail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.repository.BooksRepository
import com.example.readerapp.data.Resource
import com.example.readerapp.models.Item
import com.example.readerapp.models.MBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class State {
    Idle,
    GetBookInfo,
}

data class DetailState<T>(
    val state: State = State.Idle,
    val resource: Resource<T>? = null
)

@HiltViewModel
class DetailViewModel @Inject constructor(private val booksRepository: BooksRepository) :
    ViewModel() {
    private val _bookInfo = mutableStateOf(DetailState<Item>())
    val bookInfo = _bookInfo


    fun getBookInfo(id: String) {
        viewModelScope.launch {
            _bookInfo.value = _bookInfo.value.copy(state = State.GetBookInfo)
            val response = booksRepository.getBookInfo(bookId = id)
            _bookInfo.value = _bookInfo.value.copy(
                state = State.Idle,
                resource = response
            )
        }
    }

    fun saveBook(onSuccess: () -> Unit) {
        viewModelScope.launch {
            bookInfo.value.resource?.data?.let { item ->
                val book = MBook.fromItem(item)
                booksRepository.saveBook(book).addOnSuccessListener {
                    onSuccess.invoke()
                }.addOnFailureListener {
                    Log.d("TAG", "saveBook: ${it.message}")
                }
            }
        }
    }

    fun deleteBook(onSuccess: () -> Unit) {
        viewModelScope.launch {
            bookInfo.value.resource?.data?.let { item ->
                val book = MBook.fromItem(item)
                booksRepository.deleteBook(book).addOnSuccessListener {
                    onSuccess.invoke()
                }.addOnFailureListener {
                    Log.d("TAG", "deleteBook: ${it.message}")
                }
            }
        }
    }
}