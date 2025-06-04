package com.example.myapplication.data.repository.network

import com.example.myapplication.data.mock.Mock
import com.example.myapplication.data.model.Resource
import com.example.myapplication.data.model.search.User
import com.example.myapplication.data.network.api.GitHubApi
import com.example.myapplication.data.network.api.api
import com.example.myapplication.data.toUser
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.coroutineContext

class UserUserSearchRepositoryImpl(
    private val mock: Mock = Mock(),
    private val githubApi: GitHubApi = api,
) :
    UserSearchRepository {


    private val _users: MutableStateFlow<Resource<List<User>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val usersFlow: StateFlow<Resource<List<User>>> = _users.asStateFlow()


    override suspend fun search(query: String) {
        try {
            val response = githubApi.searchUsersByName(query)
            if (response.incompleteResults) {
                _users.value = Resource.Error("Incoplete results")
            } else {
                _users.update { users ->
                    Resource.Success(response.items.map { it.toUser() })
                }
            }
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            _users.value = Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getUserSearchFlow(): StateFlow<Resource<List<User>>> {
        return usersFlow
    }
}