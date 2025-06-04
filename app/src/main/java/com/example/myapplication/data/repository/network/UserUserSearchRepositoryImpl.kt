package com.example.myapplication.data.repository.network

import com.example.myapplication.data.mock.Mock
import com.example.myapplication.data.model.Resource
import com.example.myapplication.data.model.search.User
import kotlinx.coroutines.flow.StateFlow

class UserUserSearchRepositoryImpl(private val mock: Mock = Mock()) : UserSearchRepository {
    override suspend fun search(query: String) {
        mock.getByName(query)
    }

    override suspend fun getUserSearchFlow(): StateFlow<Resource<List<User>>> {
        return mock.usersFlow
    }
}