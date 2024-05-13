package com.uspcards.responses

import kotlinx.serialization.Serializable

@Serializable
class UserResponse (
    val id: String,
    val username: String,
    val password: String
)
