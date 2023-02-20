package com.example.readerapp.data

import com.example.readerapp.models.UserModel
import com.example.readerapp.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : AuthRepository {
    override fun signUp(email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    override fun login(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    override fun saveUser(user: UserModel) = firebaseFirestore.collection("users").add(user.toMap())

    override fun logout() = firebaseAuth.signOut()
}