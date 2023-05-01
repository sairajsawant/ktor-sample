package com.simplifiers

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.simplifiers.plugins.*
import io.ktor.serialization.gson.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    embeddedServer(Netty, port = 8081, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureContentNegotiation()
    configureRequestValidation()
}
