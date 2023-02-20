package com.example.readerapp.ui.screens.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.models.UserModel
import com.example.readerapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AuthState {
    Idle,
    Login,
    Signup,
}

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _authState = MutableStateFlow(AuthState.Idle)
    val authState = _authState

    fun signupNewUser(cxt: Context, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Signup
                authRepository.signUp(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val firebaseUser = it.result.user!!
                        val user = UserModel(
                            uid = firebaseUser.uid,
                            name = email.split("@")[0],
                            quote = "Quote of the day",
                            occupation = "Android Dev"
                        )
                        authRepository.saveUser(user)
                        onSuccess.invoke()
                    }
                    _authState.value = AuthState.Idle
                }.addOnFailureListener {
                    _authState.value = AuthState.Idle
                    Toast.makeText(cxt, it.message, Toast.LENGTH_LONG).show()
                }
            } catch (ex: Exception) {
                _authState.value = AuthState.Idle
                Log.d("FB", "signup: ${ex.message}")
            }
        }
    }

    fun loginUser(cxt: Context, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Login
                authRepository.login(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("FB", "login: Yay!!! ${it.result}")
                        onSuccess.invoke()
                    }
                    _authState.value = AuthState.Idle
                }.addOnFailureListener {
                    _authState.value = AuthState.Idle
                    Toast.makeText(cxt, it.message, Toast.LENGTH_LONG).show()
                }
            } catch (ex: Exception) {
                _authState.value = AuthState.Idle
                Log.d("FB", "login: ${ex.message}")
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        authRepository.logout()
        onSuccess.invoke()
    }
}