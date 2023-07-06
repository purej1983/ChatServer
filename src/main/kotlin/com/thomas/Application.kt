package com.thomas

import com.thomas.dao.DatabaseFactory
import com.thomas.di.mainModule
import io.ktor.server.application.*
import com.thomas.plugins.*

import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    DatabaseFactory.init(environment.config)
    install(Koin) {
        slf4jLogger()
        modules(mainModule)
    }
    configureSerialization()
    configureSockets()
    configureRouting()
}
