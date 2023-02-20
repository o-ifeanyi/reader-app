package com.example.readerapp.ui.screens.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.readerapp.data.Resource
import com.example.readerapp.ui.widgets.AppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    navController: NavController,
    bookId: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getBookInfo(id = bookId)
    }

    val detail = viewModel.bookInfo.value
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBar(
                title = "Book Details",
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.saveBook {
                                Toast.makeText(context, "Saved Successfully!", Toast.LENGTH_LONG)
                                    .show()
                            }
                        },
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = "Save Button")
                    }
                    IconButton(
                        onClick = {
                            viewModel.deleteBook {
                                Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_LONG)
                                    .show()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Button"
                        )
                    }
                },
            ) {
                navController.popBackStack()
            }
        },

        ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            when (detail.resource) {
                is Resource.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Something went wrong: ${detail.resource.message}",
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
                is Resource.Success -> {
                    val book = viewModel.bookInfo.value.resource?.data!!
                    val bookInfo = book.volumeInfo
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.Top) {
                        Image(
                            modifier = Modifier
                                .height(150.dp)
                                .width(120.dp),
                            contentScale = ContentScale.FillBounds,
                            painter = rememberAsyncImagePainter(model = bookInfo.imageLinks.smallThumbnail),
                            contentDescription = "Book Cover image"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = bookInfo.title,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = false,
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = "Authors: ${bookInfo.authors}",
                                overflow = TextOverflow.Ellipsis,
                                softWrap = false,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = "Date: ${bookInfo.publishedDate}",
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = "Category: ${bookInfo.categories}",
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    val cleanDescription = HtmlCompat.fromHtml(
                        bookInfo.description,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()
                    LazyColumn {
                        item {
                            Text(text = cleanDescription)
                        }
                    }
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
