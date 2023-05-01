package com.simplifiers

import com.simplifiers.models.Customer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.customerRoutes() {

    //mock storage - will add postgres later
    val customers = mutableListOf<Customer>()

    get("/{id}") {
        val customer: Customer?
                = customers.find { it.id == call.parameters["id"]?.toInt() }

        if (customer != null) {
            call.respond(HttpStatusCode.OK, customer)
        } else {
            call.respond(HttpStatusCode.BadRequest, "Customer does not exist!")
        }
    }

    post("/customer/add") {
        runCatching {
            call.receive<Customer>()
        }.onSuccess { customer ->
            if (customers.find { it.id == customer.id } == null) {
                customers.add(customer)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Customer with id ${customer.id} exists")
            }
        }.onFailure {
            call.respond(HttpStatusCode.BadRequest, it.message.toString())
        }
    }

}

fun RequestValidationConfig.validateCustomerRequests() {
    validate<Customer> {
        if (it.id < 0)
            ValidationResult.Invalid("Invalid customer id - should be non-negative")
        else
            ValidationResult.Valid
    }
}