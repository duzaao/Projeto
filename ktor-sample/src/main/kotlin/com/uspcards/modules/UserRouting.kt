package com.uspcards.modules

import com.uspcards.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*

import com.uspcards.models.toUserResponse
import com.uspcards.requests.UserRequest
import com.uspcards.requests.toUser
import com.uspcards.responses.UserResponse
import com.uspcards.services.UserService

import java.util.*

fun Application.configureUserRouting(
    userService: UserService
) {
    routing {
        get("/users") {
            val users = userService.getAllUsers().map{it.toUserResponse()}
            call.respond(HttpStatusCode.OK, users)
        }

        post("/user") {
            val userRequest = call.receive<UserRequest>()
            val user = userRequest.toUser()
            val id = userService.create(user)
            call.respond(HttpStatusCode.Created, id)
        }

        get("/user/{id}") {
            val id = call.parameters["id"] ?.let {UUID.fromString(it)}
                ?: throw IllegalArgumentException("Id parameter missing")
            val user = userService.read(id)
            if (user != null){
                call.respond(HttpStatusCode.OK, user.toUserResponse())
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        put("/user/{id}") {
            val id = call.parameters["id"] ?.let {UUID.fromString(it)}
                ?: throw IllegalArgumentException("Id parameter missing")
            val userRequest = call.receive<UserRequest>()
            val user = userRequest.toUser(id)
            userService.update(id, user)
            call.respond(HttpStatusCode.OK)
        }

        delete("/user/{id}") {
            val id = call.parameters["id"] ?.let {UUID.fromString(it)}
                ?: throw IllegalArgumentException("Id parameter missing")
            userService.delete(id)
            call.respond(HttpStatusCode.OK)
        }

    }
}
