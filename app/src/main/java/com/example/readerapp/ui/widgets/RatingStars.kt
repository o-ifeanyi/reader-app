package com.example.readerapp.ui.widgets

import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingStars(rating: Int, onSelect: (Int) -> Unit) {
    val selectedRating = remember {
        mutableStateOf(rating)
    }
    val pressed = remember {
        mutableStateOf(false)
    }
    val size = animateDpAsState(
        targetValue = if (pressed.value) 30.dp else 40.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Row(modifier = Modifier.fillMaxWidth().height(50.dp), horizontalArrangement = Arrangement.Center) {
        for (i in 1..5) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating Icon",
                tint = if (i <= selectedRating.value) Color.Red.copy(alpha = .5f) else Color.Black,
                modifier = Modifier.size(size.value).pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            pressed.value = true
                            selectedRating.value = i
                        }
                        MotionEvent.ACTION_UP -> {
                            pressed.value = false
                            onSelect.invoke(i)
                        }
                    }
                    true
                }
            )
        }
    }
}