package com.example.readerapp.data

import android.util.Log
import com.example.readerapp.models.Books
import com.example.readerapp.models.Item
import com.example.readerapp.models.MBook
import com.example.readerapp.repository.BooksRepository
import com.example.readerapp.repository.BooksApi
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val booksApi: BooksApi,
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
) : BooksRepository {
    override suspend fun queryBooks(query: String): Resource<Books> {
        return try {
            val response = booksApi.getAllBooks(query = query)
            Log.d("TAG", "getAllBooks: Success: $response")
            if (response.totalItems == 0) {
                return Resource.Error(message = "Item not found")
            }
            Resource.Success(data = response)
        } catch (ex: Exception) {
            Log.d("TAG", "getAllBooks: Error: ${ex.message}")
            Resource.Error(message = "${ex.message}")
        }
    }

    override suspend fun getBookInfo(bookId: String): Resource<Item> {
        return try {
            val response = booksApi.getBookInfo(bookId = bookId)
            Log.d("TAG", "getBookInfo: Success: $response")
            Resource.Success(data = response)
        } catch (ex: Exception) {
            Log.d("TAG", "getBookInfo: Error: ${ex.message}")
            Resource.Error(message = "Error: ${ex.message}")
        }
    }

    override fun saveBook(book: MBook): Task<Void> {
        return firestore.collection("books").document(book.uid!!)
            .set(book.copy(userId = firebaseAuth.uid))
    }

    override fun deleteBook(book: MBook): Task<Void> {
        return firestore.collection("books")
            .document(book.uid!!).delete()
    }

    override suspend fun getUserBooks(): List<MBook?> {
        val uid = firebaseAuth.uid
        Log.d("TAG", "getUserBooks: ====USERID = $uid===")
        return firestore.collection("books")
            .whereEqualTo("userId", uid).get().await().documents.map {
                it.toObject(MBook::class.java)
            }
    }
}