package com.example.readerapp.models

import com.google.firebase.Timestamp

data class MBook(
    val uid: String? = null,
    val userId: String? = null,
    val authors: List<String>? = null,
    val categories: List<String>? = null,
    val title: String? = null,
    val description: String? = null,
    val imageLink: String? = null,
    val language: String? = null,
    val pageCount: Int? = null,
    val publishedDate: String? = null,
    val publisher: String? = null,
    val note: String? = null,
    val startedReading: Timestamp? = null,
    val finishedReading: Timestamp? = null,
    val rating: Int? = null,
) {
    companion object {
        fun fromItem(item: Item) : MBook {
            val info = item.volumeInfo
            return  MBook(
                uid = item.id,
                userId = "",
                authors = info.authors,
                categories = info.categories,
                title = info.title,
                description = info.description,
                imageLink = info.imageLinks.thumbnail,
                language = info.language,
                pageCount = info.pageCount,
                publishedDate = info.publishedDate,
                publisher = info.publisher,
                note = "",
                startedReading = null,
                finishedReading = null,
                rating = 0,
            )
        }
    }
}
