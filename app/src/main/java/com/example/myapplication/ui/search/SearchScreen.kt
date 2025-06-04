package com.example.myapplication.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.data.model.search.User


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        SearchHeader(
            query = uiState.query,
            onQueryChanged = viewModel::updateQuery,
        )
        if (uiState.isLoading) {
            Loader()
        } else if (!uiState.error.isNullOrBlank()) {
            Error(error = uiState.error.orEmpty())
        } else {
            SearchResults(
                modifier = Modifier.weight(1f),
                users = uiState.users
            )
        }
    }

}

@Composable
private fun SearchHeader(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit
) {
    // A basic TextField acting as a search input
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon")
        },
        placeholder = { Text("Search...") },
        singleLine = true,
        shape = MaterialTheme.shapes.medium
    )
}

@Composable
private fun SearchResults(modifier: Modifier = Modifier, users: List<User>) {
    LazyColumn(modifier = modifier) {
        items(users, key = { user ->
            user.id
        }) { user ->
            Text(text = "${user.name}")
        }
    }
}

@Composable
private fun Error(modifier: Modifier = Modifier, error: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = error,
            modifier = modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.error,
        )
    }
}

@Composable
private fun Loader(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
    }
}

@Composable
private fun Empty(modifier: Modifier = Modifier) {

}