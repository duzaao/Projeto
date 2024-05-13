package com.uspcards.services

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import kotlinx.serialization.Serializable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*

import com.uspcards.models.User
import java.util.*

class UserService (database: Database){
    private object Users: Table(){
        val id = uuid("id").default(UUID.randomUUID())
        val username = varchar("username", 50)
        val password = varchar("password", 50)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database){
            SchemaUtils.create(Users)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun getAllUsers(): List<User> {
        return dbQuery {
            Users.selectAll()
                .map { row ->
                    User(
                        id = row[Users.id],
                        username = row[Users.username],
                        password = row[Users.password]
                    )
                }
        }
    }
    suspend fun create(user: User): UUID = dbQuery {
        Users.insert {
            it[id] = user.id
            it[username] = user.username
            it[password] = user.password
        }[Users.id]
    }

    suspend fun read(id: UUID): User? {
        return dbQuery{
            Users.select {Users.id eq id}
                .map { User(it[Users.id], it[Users.username], it[Users.password]) }
                .singleOrNull()
        }
    }

    suspend fun update(id: UUID, user: User){
        dbQuery {
            Users.update({ Users.id eq id }) {
                it[username] = user.username
                it[password] = user.password
            }
        }
    }

    suspend fun delete(id: UUID) {
        dbQuery {
            Users.deleteWhere { Users.id eq id }
        }
    }
}
