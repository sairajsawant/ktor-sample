package com.simplifiers

import com.simplifiers.plugins.configureContentNegotiation
import com.simplifiers.plugins.configureRequestValidation
import com.simplifiers.plugins.configureRouting
import com.simplifiers.plugins.configureServiceRegistry
import io.ktor.server.application.*
import io.ktor.server.netty.*
import redis.clients.jedis.Jedis

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    configureServiceRegistry()
    configureRouting()
    configureContentNegotiation()
    configureRequestValidation()
}
