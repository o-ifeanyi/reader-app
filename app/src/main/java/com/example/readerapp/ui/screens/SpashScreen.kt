package com.example.readerapp.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readerapp.navigation.AppScreens
import com.example.readerapp.ui.widgets.TextLogo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = .9f,
            animationSpec = tween(
                durationMillis = 600,
                easing = { OvershootInterpolator(6f).getInterpolation(it) })
        )

        delay(1500L)
        navController.navigate(
            if (FirebaseAuth.getInstance().currentUser == null)
                AppScreens.AuthScreen.name else AppScreens.HomeScreen.name) {
            popUpTo(AppScreens.SplashScreen.name) { inclusive = true }
        }
    }

    Surface(
        modifier = Modifier
            .size(250.dp)
            .scale(scale.value),
        shape = CircleShape,
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextLogo()
            Text(
                text = "Read, Change, Grow",
                style = MaterialTheme.typography.h5,
                color = Color.LightGray
            )
        }
    }
}