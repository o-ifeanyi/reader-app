package com.example.readerapp.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readerapp.data.Resource
import com.example.readerapp.models.MBook
import com.example.readerapp.navigation.AppScreens
import com.example.readerapp.ui.widgets.AppBar
import com.example.readerapp.ui.widgets.BookListItem
import com.example.readerapp.ui.widgets.InputField

@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    val search = viewModel.allBooks.value
    val searchInput = rememberSaveable { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            AppBar(title = "Search Books") {
                navController.popBackStack()
            }
        },
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(10.dp))
            InputField(input = searchInput, label = "Search", imeAction = ImeAction.Search) {
                viewModel.getAllBooks(searchQuery = searchInput.value)
                searchInput.value = ""
                keyboard?.hide()
            }
            if (search.state == State.GetAllBooks) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (search.resource is Resource.Error) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Something went wrong: ${search.resource.message}", style = MaterialTheme.typography.h6)
                }
            } else if (search.resource is Resource.Success) {
                Spacer(modifier = Modifier.height(20.dp))
                LazyColumn {
                    items(search.resource.data!!.items) { item ->
                        val book = MBook.fromItem(item)
                        BookListItem(book) {
                            navController.navigate(AppScreens.DetailScreen.name + "/${book.uid}")
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Search for a book", style = MaterialTheme.typography.h6)
                }
            }
        }
    }
}

