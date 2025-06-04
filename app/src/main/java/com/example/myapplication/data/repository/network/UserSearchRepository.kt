package com.example.myapplication.data.repository.network

import com.example.myapplication.data.model.Resource
import com.example.myapplication.data.model.search.User
import kotlinx.coroutines.flow.StateFlow

interface UserSearchRepository {

    suspend fun search(query: String)

    suspend fun getUserSearchFlow(): StateFlow<Resource<List<User>>>
}