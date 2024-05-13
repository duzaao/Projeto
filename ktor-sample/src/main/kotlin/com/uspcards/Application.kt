package com.uspcards

import com.uspcards.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

import com.uspcards.modules.configureNoteRouting
import com.uspcards.services.NoteService

import com.uspcards.modules.configureUserRouting
import com.uspcards.services.UserService

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.Database

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {

    val database = Database.connect(
        url = "jdbc:h2:file:./database/db;MODE=MySQL",
        user = "root",
        driver = "org.h2.Driver",
        password = ""
    )
    val service = NoteService(database)
    val userService = UserService(database)


    configureHTTP()
    configureRouting()
    configureSerialization()
    configureNoteRouting(service)
    configureUserRouting(userService)
}
