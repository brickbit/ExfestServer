package api.service

import model.service.RequestService
import repository.service.ServicesRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("$SERVICES/{id}")

data class ServiceDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.servicesDetail(db: ServicesRepository) {
    authenticate("jwt") {
        get<ServiceDetail> { item ->
            when (db.services().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Service with id ${item.id} not found"))
                else -> {
                    call.respond(db.service(item.id)!!)
                }
            }
        }
        delete<ServiceDetail> { item ->
            when (db.services().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Service with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Service $item.id deleted")
                }
            }
        }
        post<ServiceDetail> { item ->
            val request = call.receive<RequestService>()
            when (db.services().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Service with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.name,
                        request.cost,
                        request.description,
                        request.granted,
                        request.company,
                        request.rating
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}