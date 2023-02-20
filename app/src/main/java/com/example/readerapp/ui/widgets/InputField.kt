package com.example.readerapp.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    input: MutableState<String>,
    label: String,
    imeAction: ImeAction,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = 1,
    onAction: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = input.value,
        shape = RoundedCornerShape(20),
        onValueChange = {
            input.value = it
        },
        maxLines = maxLines,
        label = { Text(text = label) },
        enabled = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onAny = {
                onAction.invoke()
            }
        ),
        visualTransformation = visualTransformation
    )
}