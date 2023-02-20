package com.example.readerapp.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readerapp.navigation.AppScreens
import com.example.readerapp.ui.widgets.InputField
import com.example.readerapp.ui.widgets.TextLogo
import com.example.readerapp.ui.widgets.WideButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel()) {
    val emailInput = rememberSaveable { mutableStateOf("") }
    val passwordInput = rememberSaveable { mutableStateOf("") }
    val passwordFocus = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    val isSignup = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val valid = remember(emailInput.value, passwordInput.value) {
        emailInput.value.trim().isNotEmpty() && passwordInput.value.trim().isNotEmpty()
    }

    val state = authViewModel.authState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextLogo()
        InputField(input = emailInput, label = "Email", imeAction = ImeAction.Next) {
            passwordFocus.requestFocus()
            Log.d("TAG", "AuthScreen: here")
        }
        Spacer(modifier = Modifier.height(15.dp))
        InputField(
            modifier = Modifier.focusRequester(passwordFocus),
            input = passwordInput,
            label = "Password",
            imeAction = ImeAction.Done
        ) {
            keyboard?.hide()
        }
        Spacer(modifier = Modifier.height(25.dp))
        if (state != AuthState.Idle) CircularProgressIndicator() else WideButton(
            label = if (isSignup.value) "Sign up" else "Login",
            active = valid
        ) {
            keyboard?.hide()
            if (isSignup.value) {
                authViewModel.signupNewUser(
                    context,
                    emailInput.value,
                    passwordInput.value
                ) {
                    navController.navigate(AppScreens.HomeScreen.name) {
                        popUpTo(AppScreens.AuthScreen.name) { inclusive = true }
                    }
                }
            } else {
                authViewModel.loginUser(context, emailInput.value, passwordInput.value) {
                    navController.navigate(AppScreens.HomeScreen.name) {
                        popUpTo(AppScreens.AuthScreen.name) { inclusive = true }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.clickable {
            isSignup.value = !isSignup.value
        }) {
            Text(
                text = if (isSignup.value) "Have an account?" else "New User?",
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = if (isSignup.value) "Login" else "Sign up",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}



