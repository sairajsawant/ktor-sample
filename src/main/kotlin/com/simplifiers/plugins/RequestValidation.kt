package com.simplifiers.plugins

import com.simplifiers.validateCustomerRequests
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validateCustomerRequests()
    }
}
