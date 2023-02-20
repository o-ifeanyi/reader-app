package com.example.readerapp.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.readerapp.models.MBook


@Composable
fun BookCard(book: MBook, onClicked: () -> Unit) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .height(250.dp)
            .width(220.dp)
            .padding(end = 20.dp)
            .clickable { onClicked.invoke() }
    ) {
        Column(verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(topStartPercent = 40))
                    .height(30.dp)
                    .background(color = MaterialTheme.colors.primaryVariant.copy(alpha = .5f)),
            ) {
                Text(text = "Reading", modifier = Modifier.padding(horizontal = 15.dp))
            }
        }
        Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {
            Card(
                elevation = 2.dp,
                shape = CircleShape,
                modifier = Modifier.padding(10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Star Icon")
                    Text(text = "${book.rating}.0", style = MaterialTheme.typography.body1)
                }
            }
        }
        Column(modifier = Modifier.padding(10.dp)) {
            Image(
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp)
                    .clip(shape = RoundedCornerShape(topStartPercent = 20)),
                painter = rememberAsyncImagePainter(model = book.imageLink),
                contentDescription = "Book Cover image"
            )
            Text(
                text = "${book.title}",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "By ${book.authors}",
                overflow = TextOverflow.Ellipsis,
                softWrap = false,
                style = MaterialTheme.typography.caption,
            )
        }
    }
}