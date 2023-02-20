package com.example.readerapp.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readerapp.models.MBook
import com.example.readerapp.ui.widgets.BookCard
import com.example.readerapp.navigation.AppScreens
import com.example.readerapp.ui.screens.auth.AuthViewModel
import com.example.readerapp.ui.widgets.AppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val userBooks = homeViewModel.userBooks.value
    val refreshScope = rememberCoroutineScope()
    val refreshing = remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing.value = true
        homeViewModel.getUserBooks()
        refreshing.value = false
    }

    val state = rememberPullRefreshState(refreshing = refreshing.value, onRefresh = ::refresh)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state)
    ) {
        Scaffold(
            topBar = {
                AppBar(
                    title = "A. Reader",
                    hasBackButton = false,
                    actions = {
                        IconButton(
                            onClick = {
                                authViewModel.logout {
                                    navController.navigate(AppScreens.AuthScreen.name) {
                                        popUpTo(AppScreens.HomeScreen.name) { inclusive = true }
                                    }
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = "Logout Button"
                            )
                        }
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate(AppScreens.SearchScreen.name) }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Button")
                }
            },
        ) {
            if (userBooks.state == State.GetUserBooks) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                val books = userBooks.data ?: emptyList()
                val currentlyReading = books.filter {
                    it?.startedReading != null && it.finishedReading == null
                }
                val notReading = books.filter {
                    it?.startedReading == null && it?.finishedReading == null
                }
                LazyColumn {
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Your reading \nactivity right now...",
                                style = MaterialTheme.typography.h5,
                            )
                            Column {
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colors.primaryVariant
                                ) {
                                    IconButton(onClick = {
                                        navController.navigate(AppScreens.ProfileScreen.name)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Profile Button"
                                        )
                                    }

                                }
                                Text(
                                    text = "Ifeanyi",
                                    style = MaterialTheme.typography.body1,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        HorizontalBookScroll(
                            currentlyReading,
                            emptyStateText = "Books you are reading\n would appear here",
                            navController = navController
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            text = "Reading list",
                            style = MaterialTheme.typography.h5,
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        HorizontalBookScroll(
                            books = notReading,
                            emptyStateText = "Your saved books\n would appear here",
                            navController = navController
                        )
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = refreshing.value,
            state = state,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun HorizontalBookScroll(
    books: List<MBook?>,
    emptyStateText: String,
    navController: NavController
) {
    if (books.isEmpty()) {
        Box(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = emptyStateText,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
    } else {
        LazyRow(contentPadding = PaddingValues(horizontal = 20.dp)) {
            items(books) { book ->
                book?.let {
                    BookCard(it) {
                        navController.navigate(AppScreens.UpdateScreen.name + "/${book.uid}")
                    }
                } ?: Box {}
            }
        }
    }
}
