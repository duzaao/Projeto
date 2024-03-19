import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

data class User(val email: String, val password: String)
data class Credentials(val email: String, val password: String)

fun main() {
    Database.connect("jdbc:mysql://localhost:3306/usp_cards?useSSL=false", driver = "com.mysql.cj.jdbc.Driver", user = "your_username", password = "your_password")

    transaction {
        SchemaUtils.create(Users)
    }

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson { }
        }
        install(StatusPages) {
            exception<Throwable> { cause ->
                call.respond(HttpStatusCode.InternalServerError, cause.localizedMessage)
            }
        }
        routing {
            post("/inscrever") {
                val user = call.receive<User>()
                // Lógica para salvar o usuário no banco de dados
                // Exemplo: transaction { Users.insert { it[email] = user.email; it[password] = user.password } }
                println("Usuário inscrito: ${user.email}")
                call.respond(HttpStatusCode.OK, "Usuário inscrito com sucesso.")
            }

            post("/login") {
                val credentials = call.receive<Credentials>()
                // Lógica para verificar as credenciais
                // Exemplo: val user = transaction { Users.select { Users.email eq credentials.email and (Users.password eq credentials.password) }.singleOrNull() }
                // Se o usuário for encontrado, responda com sucesso, caso contrário, responda com erro.
                call.respond(HttpStatusCode.OK, "Login realizado com sucesso.")
            }
        }
    }.start(wait = true)
}