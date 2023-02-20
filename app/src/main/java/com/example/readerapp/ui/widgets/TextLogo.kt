package com.example.readerapp.ui.widgets

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TextLogo(
    text: String = "A. Reader",
    textStyle: TextStyle = MaterialTheme.typography.h3
) {
    Text(
        text = text,
        style = textStyle,
        color = Color.Red.copy(alpha = .5f),
        fontWeight = FontWeight.SemiBold
    )
}