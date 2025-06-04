package com.example.myapplication.data

import com.example.myapplication.data.model.search.User
import com.example.myapplication.data.network.dto.UserItemDto

fun UserItemDto.toUser(): User {
    return User(
        id = id,
        name = login,
        avatarUrl = avatarUrl
    )
}