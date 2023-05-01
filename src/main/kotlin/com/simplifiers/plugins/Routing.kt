package com.simplifiers.plugins

import com.simplifiers.customerRoutes
import com.simplifiers.models.Customer
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    routing {
        customerRoutes()
    }
}
