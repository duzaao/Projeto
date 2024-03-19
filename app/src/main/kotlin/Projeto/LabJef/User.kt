import org.jetbrains.exposed.dao.IntIdTable

object Users : IntIdTable() {
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
}