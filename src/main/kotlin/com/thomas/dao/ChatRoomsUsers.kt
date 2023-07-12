package com.thomas.dao

import org.jetbrains.exposed.sql.Table

object ChatRoomsUsers : Table(name = "chatrooms_users") {
    val chatrooms = reference("chatroom", ChatRooms)
    val users = reference("user", Users)
}