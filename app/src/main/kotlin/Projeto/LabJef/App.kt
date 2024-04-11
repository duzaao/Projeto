import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.http.HttpStatusCode

import java.sql.*

fun main() {
    createDatabaseIfNotExists()
    embeddedServer(Netty, port = 8080) {
        install(DefaultHeaders)
        install(CallLogging)
        install(Routing) {
            route("/signup") {
                post {
                    val email = call.parameters["email"] ?: return@post call.respondText("Missing email", status = HttpStatusCode.BadRequest)
                    val password = call.parameters["password"] ?: return@post call.respondText("Missing password", status = HttpStatusCode.BadRequest)
                    if (saveUser(email, password)) {
                        call.respondText("User registered successfully")
                    } else {
                        call.respondText("Failed to register user", status = HttpStatusCode.InternalServerError)
                    }
                }
            }
        }
    }.start(wait = true)
}

fun createDatabaseIfNotExists() {
    val url = "jdbc:mysql://localhost:3306/"
    val user = "teste"
    val password = "senha"
    val databaseName = "USPCARDS"

    try {
        Class.forName("com.mysql.cj.jdbc.Driver")
        val connection = DriverManager.getConnection(url, user, password)
        val statement = connection.createStatement()
        statement.executeUpdate("CREATE DATABASE IF NOT EXISTS $databaseName")
        statement.close()
        connection.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun saveUser(email: String, password: String): Boolean {
    val url = "jdbc:mysql://localhost:3306/USPCARDS"
    val user = "teste"
    val password = "senha"

    return try {
        Class.forName("com.mysql.cj.jdbc.Driver")
        val connection = DriverManager.getConnection(url, user, password)
        val statement = connection.prepareStatement("INSERT INTO users (email, password) VALUES (?, ?)")
        statement.setString(1, email)
        statement.setString(2, password)
        statement.executeUpdate()
        statement.close()
        connection.close()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}
