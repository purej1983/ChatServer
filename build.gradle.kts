val ktor_version: String by project
val logback_version: String by project
val koin_version: String by project
val kotlin_version: String by project
plugins {
    id ("org.jetbrains.kotlin.jvm") version "1.8.22"
    id ("io.ktor.plugin") version "2.3.2"
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.8.22"
}

group "com.thomas"
version "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    project.setProperty("mainClassName", mainClass.get())

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation ("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation ("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation ("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation ("io.ktor:ktor-server-websockets-jvm:$ktor_version")
    implementation ("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation ("ch.qos.logback:logback-classic:$logback_version")
    implementation ("io.insert-koin:koin-core:$koin_version")
    implementation ("io.insert-koin:koin-ktor:$koin_version")
    implementation ("io.insert-koin:koin-logger-slf4j:$koin_version")
    testImplementation ("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation ("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

ktor {
    docker {
        localImageName.set("ChatServer")
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
        portMappings.set(listOf(
                io.ktor.plugin.features.DockerPortMapping(
                        80,
                        8080,
                        io.ktor.plugin.features.DockerPortMappingProtocol.TCP
                )
        ))
    }
}