package com.uspcards.requests

import com.uspcards.models.User

import kotlinx.serialization.Serializable

import java.util.UUID

@Serializable
class UserRequest (
    val username: String,
    val password: String
)

fun UserRequest.toUser(
    id: UUID = UUID.randomUUID()
): User {
    return User(
        id = id,
        username = username,
        password = password
    )
}
