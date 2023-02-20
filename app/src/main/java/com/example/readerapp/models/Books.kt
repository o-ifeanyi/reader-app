package com.example.readerapp.models

data class Books(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)