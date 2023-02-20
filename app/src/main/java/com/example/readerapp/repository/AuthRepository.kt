package com.example.readerapp.repository

import com.example.readerapp.models.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference

interface AuthRepository {
    fun signUp(email: String, password: String) : Task<AuthResult>

    fun login(email: String, password: String) : Task<AuthResult>

    fun saveUser(user: UserModel) : Task<DocumentReference>

    fun logout()
}