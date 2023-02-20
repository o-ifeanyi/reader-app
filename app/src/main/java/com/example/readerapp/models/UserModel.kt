package com.example.readerapp.models

data class UserModel(
    val uid: String,
    val name: String,
    val quote: String,
    val occupation: String
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "uid" to this.uid,
            "name" to this.name,
            "quote" to this.quote,
            "occupation" to this.occupation,
        )
    }
}
