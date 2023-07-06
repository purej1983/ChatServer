package com.thomas.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

class User(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<User>(Users)

    var name by Users.name
    var createdAt by Users.createdAt
    var updatedAt by Users.updatedAt
    var joinedChatRooms by ChatRoom via ChatRoomsUsers
}
object Users : LongIdTable() {
    val name: Column<String> = varchar("name", length = 50)
    val createdAt: Column<LocalDateTime> = datetime("date_created").defaultExpression(CurrentDateTime)
    val updatedAt: Column<LocalDateTime> = datetime("date_updated").defaultExpression(CurrentDateTime)
}