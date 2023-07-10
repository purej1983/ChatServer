package com.thomas.dao

import io.ktor.server.config.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("storage.driverClassName").getString()
        val connectionString = config.property("storage.connectionString").getString()
        val dbUser = config.property("storage.dbUsername").getString()
        val dbPass = config.property("storage.dbPassword").getString()
        val database = Database.connect(
            url = connectionString,
            driver = driverClassName,
            user = dbUser,
            password = dbPass
        )

        transaction(database) {
            SchemaUtils.create(ChatRooms, Users, ChatRoomsUsers)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}