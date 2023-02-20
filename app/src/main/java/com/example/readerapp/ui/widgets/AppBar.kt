package com.example.readerapp.ui.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun AppBar(
    title: String,
    actions: @Composable (RowScope.() -> Unit) = {},
    hasBackButton: Boolean = true,
    onBackPressed: () -> Unit = {},
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        navigationIcon = if (hasBackButton) {
            {
                IconButton(
                    onClick = onBackPressed,
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
            }
        } else {
            null
        },
        title = {
            TextLogo(
                text = title,
                textStyle = MaterialTheme.typography.h5,
            )
        }, actions = actions
    )
}