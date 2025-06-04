package com.example.myapplication.data.network.api

import com.example.myapplication.data.TOKEN
import com.example.myapplication.data.network.API_BASE_URL
import com.example.myapplication.data.network.HttpClientBuilder
import com.example.myapplication.data.network.dto.SearchUsersResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

// GitHub API wrapper
class GitHubApi(private val client: HttpClient, private val githubToken: String? = null) {

    suspend fun searchUsersByName(queryName: String, perPage: Int = 500): SearchUsersResponse {
        return client.get("$API_BASE_URL/search/users") {
            parameter("q", "$queryName in:login")
            parameter("per_page", perPage)
            header("Accept", "application/vnd.github.v3+json")
            githubToken?.let {
                header("Authorization", "token $it")
            }
        }.body()
    }
}


val api = GitHubApi(HttpClientBuilder.build(), githubToken = TOKEN)