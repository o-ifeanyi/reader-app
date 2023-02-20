package com.example.readerapp.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight


@Composable
fun ShowDialog(
    openState: MutableState<Boolean>,
    title: String,
    subtitle: String,
    dismissText: String = "Cancel",
    confirmText: String = "Continue",
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { openState.value = false },
        title = { Text(text = title, fontWeight = FontWeight.SemiBold) },
        text = {
            Text(text = subtitle)
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = { openState.value = false }) {
                    Text(text = dismissText)
                }
                TextButton(onClick = {
                    openState.value = false
                    onConfirm.invoke()
                }) {
                    Text(text = confirmText)
                }
            }
        }
    )
}