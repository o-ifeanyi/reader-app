package com.example.readerapp.ui.screens.update

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.readerapp.ui.screens.home.HomeViewModel
import com.example.readerapp.ui.widgets.*
import com.example.readerapp.utils.formatDate
import com.google.firebase.Timestamp

@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UpdateScreen(
    bookId: String,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    updateViewModel: UpdateViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current
    var book = try {
        homeViewModel.userBooks.value.data?.first {
            it?.uid == bookId
        }
    } catch (ex: Exception) {
        Log.d("TAG", "UpdateScreen: ${ex.message}")
        null
    }
    val startedReading = remember(book) {
        mutableStateOf(book?.startedReading != null)
    }
    val finishedReading = remember(book) {
        mutableStateOf(book?.finishedReading != null)
    }
    val noteInput = remember(book) {
        mutableStateOf(book?.note ?: "")
    }
    val showDeleteDialog = remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            AppBar(title = "Update Book") {
                navController.popBackStack()
            }
        },
    ) {
        if (book == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (showDeleteDialog.value) {
                ShowDialog(
                    openState = showDeleteDialog,
                    title = "Delete Book?",
                    subtitle = "This action cannot be undone."
                ) {
                    updateViewModel.deleteBook(book!!) {
                        Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_LONG)
                            .show()
                        navController.popBackStack()
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        modifier = Modifier
                            .height(150.dp)
                            .width(120.dp),
                        contentScale = ContentScale.FillBounds,
                        painter = rememberAsyncImagePainter(model = book!!.imageLink),
                        contentDescription = "Book Cover image"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "${book!!.title}",
                            overflow = TextOverflow.Ellipsis,
                            softWrap = false,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Authors: ${book!!.authors}",
                            overflow = TextOverflow.Ellipsis,
                            softWrap = false,
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Date: ${book!!.publishedDate}",
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                InputField(
                    input = noteInput,
                    label = "Leave a review",
                    imeAction = ImeAction.Done,
                    maxLines = 5,
                ) {
                    keyboard?.hide()
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextButton(
                        onClick = {
                            book = book?.copy(startedReading = Timestamp.now())
                            startedReading.value = true
                        },
                        enabled = !startedReading.value
                    ) {
                        Text(text = if (startedReading.value) "Started on: ${formatDate(book!!.startedReading)}" else "Start reading")
                    }
                    TextButton(
                        onClick = {
                            book = book?.copy(finishedReading = Timestamp.now())
                            finishedReading.value = true
                        },
                        enabled = !finishedReading.value
                    ) {
                        Text(text = if (finishedReading.value) "Finished on: ${formatDate(book!!.startedReading)}" else "Mark as read")
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Rating", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(15.dp))
                RatingStars(rating = book!!.rating ?: 0) {
                    book = book?.copy(rating = it)
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    WideButton(label = "Delete", active = true, fraction = .4f) {
                        showDeleteDialog.value = true
                    }
                    WideButton(label = "Update", active = true, fraction = .6f) {
                        book = book?.copy(note = noteInput.value)
                        updateViewModel.saveBook(book!!) {
                            Toast.makeText(context, "Saved Successfully!", Toast.LENGTH_LONG)
                                .show()
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}

