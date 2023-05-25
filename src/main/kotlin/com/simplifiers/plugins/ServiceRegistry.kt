package com.simplifiers.plugins

import io.ktor.server.application.*
import redis.clients.jedis.Jedis

fun Application.configureServiceRegistry() {
    val jedis = Jedis(
        environment.config.property("ktor.redis.host").getString(),
        environment.config.property("ktor.redis.port").getString().toInt()
    )
    val serviceRegistry = jedis.smembers("ktor-sample")
    val host = environment.config.property("ktor.deployment.host").getString()
    val port = environment.config.property("ktor.deployment.port").getString()

    if (!serviceRegistry.contains("$host:$port")) {
        jedis.sadd("ktor-sample", "$host:$port")
    }
}