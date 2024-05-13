package com.uspcards.models

import java.util.*
import com.uspcards.responses.UserResponse
import kotlinx.serialization.Contextual

import kotlinx.serialization.Serializable

@Serializable
class User (
    @Contextual
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val password: String,
)

fun User.toUserResponse(): UserResponse{
    return UserResponse(
        id = id.toString(),
        username = username,
        password = password
    )
}
