package com.thomas.dao
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

class ChatRoom(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ChatRoom>(ChatRooms)

    var name by ChatRooms.name
    var createdAt by ChatRooms.createdAt
    var updatedAt by ChatRooms.updatedAt
    var usersJoined by User via ChatRoomsUsers
}
object ChatRooms : LongIdTable() {
    val name: Column<String> = varchar("name", length = 50)
    val createdAt: Column<LocalDateTime> = datetime("date_created").defaultExpression(CurrentDateTime)
    val updatedAt: Column<LocalDateTime> = datetime("date_updated").defaultExpression(CurrentDateTime)
}