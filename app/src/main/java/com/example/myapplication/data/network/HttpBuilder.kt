package com.example.myapplication.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


// Separate HttpClient builder
object HttpClientBuilder {
    fun build(): HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
//        install(Logging) {
//            level = LogLevel.INFO
//        }
    }
}