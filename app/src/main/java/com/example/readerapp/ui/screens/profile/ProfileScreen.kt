package com.example.readerapp.ui.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readerapp.navigation.AppScreens
import com.example.readerapp.ui.screens.home.HomeViewModel
import com.example.readerapp.ui.screens.home.State
import com.example.readerapp.ui.widgets.AppBar
import com.example.readerapp.ui.widgets.BookListItem
import com.example.readerapp.utils.pluralFor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navController: NavController, homeViewModel: HomeViewModel = hiltViewModel()) {
    val userBooks = homeViewModel.userBooks.value

    Scaffold(
        topBar = {
            AppBar(title = "Profile") {
                navController.popBackStack()
            }
        },
    ) {
        if (userBooks.state == State.GetUserBooks) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val reading = userBooks.data?.filter {
                it?.startedReading != null && it.finishedReading == null
            }?: emptyList()
            val finished = userBooks.data?.filter {
                it?.finishedReading != null
            } ?: emptyList()
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Your Stats",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Divider()
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "You are reading: ${reading.size} ${pluralFor("Book", reading)}")
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "You have read: ${finished.size} ${pluralFor("Book", finished)}")
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Books you have read",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn {
                    items(finished) { book ->
                        book?.let { it1 ->
                            BookListItem(it1) {
                                navController.navigate(AppScreens.DetailScreen.name + "/${book.uid}")
                            }
                        }
                    }
                }
            }
        }
    }
}