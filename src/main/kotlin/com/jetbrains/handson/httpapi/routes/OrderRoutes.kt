package com.jetbrains.handson.httpapi.routes

import com.jetbrains.handson.httpapi.models.orderStorage
import io.ktor.application.*
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*


fun Route.getOrderRoute() {
    get("/order/{id}") {
        val id = call.parameters["id"] ?: ""
        val order = orderStorage.find { it.number.compareTo(id) == 0 }
        if (order != null) {
            call.respond(order)
        } else {
            call.respondText("Not Found", status = HttpStatusCode.NotFound)
        }
    }
}

fun Route.totalizeOrderRoute() {
    get("/order/{id}/total") {
        val id = call.parameters["id"] ?: ""
        val order = orderStorage.find { it.number.compareTo(id) == 0 }
        if (order != null) {
            val total = order.contents.map { it.price * it.amount}.sumByDouble { it }
            call.respondText("Total for order is $total")
        } else {
            call.respondText("Not Found", status = HttpStatusCode.NotFound)
        }
    }
}

fun Application.registerOrderRoutes() {
    routing {
        getOrderRoute()
        totalizeOrderRoute()
    }
}