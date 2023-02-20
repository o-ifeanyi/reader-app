package com.example.readerapp.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.readerapp.models.MBook


@Composable
fun BookListItem(book: MBook, onClicked: () -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.padding(vertical = 10.dp).clickable { onClicked.invoke() },
        shape = RoundedCornerShape(10)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Image(
                modifier = Modifier
                    .height(85.dp)
                    .width(60.dp),
                painter = rememberAsyncImagePainter(model = book.imageLink),
                contentDescription = "Book Cover image"
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "${book.title}",
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Authors: ${book.authors}",
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    style = MaterialTheme.typography.caption
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Date: ${book.publishedDate}",
                    style = MaterialTheme.typography.caption
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Category: ${book.categories}",
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}