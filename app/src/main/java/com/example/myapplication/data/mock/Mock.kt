package com.example.myapplication.data.mock

import com.example.myapplication.data.model.Resource
import com.example.myapplication.data.model.search.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Mock {

    val users = listOf(
        User(1L, "John", "Doe"),
        User(2L, "Jane", "Doe"),
        User(3L, "Bob", "Smith"),
        User(4L, "Liam", "White"),
        User(5L, "Sophia", "Harris"),
        User(6L, "Mason", "Martin"),
        User(7L, "Isabella", "Thompson"),
        User(8L, "Noah", "Garcia"),
        User(9L, "Mia", "Martinez"),
        User(10L, "Lucas", "Robinson"),
        User(11L, "Charlotte", "Clark"),
        User(12L, "Aiden", "Rodriguez"),
        User(13L, "Amelia", "Lewis"),
        User(14L, "Elijah", "Lee"),
        User(15L, "Harper", "Walker"),
        User(16L, "Oliver", "Hall"),
        User(17L, "Evelyn", "Allen"),
        User(18L, "James", "Young"),
        User(19L, "Abigail", "King"),
        User(20L, "Benjamin", "Wright"),
        User(21L, "Ella", "Scott"),
        User(22L, "William", "Green"),
        User(23L, "Avery", "Baker"),
        User(24L, "Henry", "Adams"),
        User(25L, "Scarlett", "Nelson"),
        User(26L, "Alexander", "Hill"),
        User(27L, "Lily", "Ramirez"),
        User(28L, "Daniel", "Campbell"),
        User(29L, "Aria", "Mitchell"),
        User(30L, "Sebastian", "Roberts"),
        User(31L, "Emily", "Carter"),
        User(32L, "Matthew", "Phillips"),
        User(33L, "Sofia", "Evans")
    )


    private val _users: MutableStateFlow<Resource<List<User>>> =
        MutableStateFlow(Resource.Success(users))
    val usersFlow: StateFlow<Resource<List<User>>> = _users.asStateFlow()


    suspend fun getByName(query: String) {
        delay(2000)
        _users.update {
            if (query.isBlank()) {
                return@update Resource.Success(users)
            } else {
                Resource.Success(users.filter {
                    val nameAndSurname = "${it.name} ${it.surname}"
                    nameAndSurname.contains(
                        query,
                        ignoreCase = true
                    )
                })
            }
        }
    }
}