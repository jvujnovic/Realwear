package com.example.myapplication.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Resource
import com.example.myapplication.data.model.search.User
import com.example.myapplication.data.repository.network.UserSearchRepository
import com.example.myapplication.data.repository.network.UserUserSearchRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val repository: UserSearchRepository = UserUserSearchRepositoryImpl()) :
    ViewModel() {

    private val _uiState = MutableStateFlow(UserSeachUIState())
    val uiState: StateFlow<UserSeachUIState> = _uiState

    private var searchJob: Job? = null


    init {
        initializeSearchResults()
    }

    private fun initializeSearchResults() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = true)
            }
            repository.getUserSearchFlow().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                users = result.data,
                                isLoading = false,
                                error = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = result.message,
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateQuery(query: String) {
        Log.d("mytest", "updateQuery: $query")
        _uiState.update { state ->
            state.copy(query = query)
        }
        Log.d("mytest", "state: ${_uiState.value.query}")
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(600)
            _uiState.update { state ->
                state.copy(isLoading = true)
            }
            withContext(Dispatchers.IO) {
                repository.search(query)
            }
        }
    }

    data class UserSeachUIState(
        val query: String = "",
        val users: List<User> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

}