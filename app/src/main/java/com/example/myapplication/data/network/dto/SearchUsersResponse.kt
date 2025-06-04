package com.example.myapplication.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SearchUsersResponse(
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("incomplete_results")
    val incompleteResults: Boolean,
    @SerialName("items") val items: List<UserItemDto>
)
