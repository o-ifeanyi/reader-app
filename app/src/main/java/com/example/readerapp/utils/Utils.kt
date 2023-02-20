package com.example.readerapp.utils

import android.icu.text.DateFormat
import com.google.firebase.Timestamp

fun formatDate(timestamp: Timestamp? = Timestamp.now()): String {
    return if (timestamp != null) {
        DateFormat.getDateInstance().format(timestamp.toDate()).toString().split(",")[0]
    } else {
        ""
    }
}

fun pluralFor(noun : String?, list: List<Any?>?) : String {
    if (noun == null || noun == "") return ""

    if (list.isNullOrEmpty()) return "${noun}s"
    return if (list.size == 1)
        noun
    else
        "${noun}s"
}