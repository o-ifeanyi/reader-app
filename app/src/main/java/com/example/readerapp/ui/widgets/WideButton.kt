package com.example.readerapp.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WideButton(label: String, active: Boolean, fraction: Float = 1.0f, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = active,
        modifier = Modifier.fillMaxWidth(fraction),
        shape = RoundedCornerShape(20)
    ) {
        Text(text = label, modifier = Modifier.padding(vertical = 10.dp))
    }
}