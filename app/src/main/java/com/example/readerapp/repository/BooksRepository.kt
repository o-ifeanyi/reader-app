package com.example.readerapp.repository

import com.example.readerapp.data.Resource
import com.example.readerapp.models.Books
import com.example.readerapp.models.Item
import com.example.readerapp.models.MBook
import com.google.android.gms.tasks.Task

interface BooksRepository {
    suspend fun queryBooks(query: String): Resource<Books>

    suspend fun getBookInfo(bookId: String): Resource<Item>

    fun saveBook(book: MBook): Task<Void>

    fun deleteBook(book: MBook): Task<Void>

    suspend fun getUserBooks(): List<MBook?>
}