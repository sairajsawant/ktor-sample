package com.simplifiers

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.simplifiers.models.Customer
import com.simplifiers.sqldelight.SqlDelight
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Routing.customerRoutes() {

    //mock storage - will add postgres later
    val customers = mutableListOf<Customer>()

    val dataSource = HikariDataSource(
        HikariConfig(
            Properties().apply {
                setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource")
                setProperty("dataSource.databaseName", "mydb")
            }
        )
    )

    val driver: SqlDriver = dataSource.asJdbcDriver()

    println(SqlDelight(driver).customerSchemaQueries.selectAll().executeAsList())

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
                //customers.add(customer)
                SqlDelight(driver).customerSchemaQueries.insertCustomer(
                    com.simplifiers.sqldelight.Customer(customer.id, customer.name)
                )
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