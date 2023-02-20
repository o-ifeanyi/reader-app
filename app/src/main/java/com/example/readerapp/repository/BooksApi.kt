package com.example.readerapp.repository

import com.example.readerapp.models.Books
import com.example.readerapp.models.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Books

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Item
}