package com.example.readerapp.ui.screens.update

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.models.MBook
import com.example.readerapp.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(private val booksRepository: BooksRepository) :
    ViewModel() {
    fun saveBook(book: MBook, onSuccess: () -> Unit) {
        viewModelScope.launch {
            booksRepository.saveBook(book).addOnSuccessListener {
                onSuccess.invoke()
            }.addOnFailureListener {
                Log.d("TAG", "saveBook: ${it.message}")
            }

        }
    }

    fun deleteBook(book: MBook, onSuccess: () -> Unit) {
        viewModelScope.launch {
            booksRepository.deleteBook(book).addOnSuccessListener {
                onSuccess.invoke()
            }.addOnFailureListener {
                Log.d("TAG", "deleteBook: ${it.message}")
            }
        }

    }
}