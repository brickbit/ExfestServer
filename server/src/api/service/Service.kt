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

const val SERVICES = "/services"
@KtorExperimentalLocationsAPI
@Location(SERVICES)

class Service

@KtorExperimentalLocationsAPI
fun Route.services(db: ServicesRepository) {
    authenticate("jwt") {
        get<Service> {
            val services = db.services()
            call.respond(services)
        }
        post<Service> {
            val request = call.receive<RequestService>()
            db.add(
                request.name,
                request.cost,
                request.description,
                request.granted,
                request.company,
                request.rating)
            call.respond(HttpStatusCode.Created)
        }
        delete<Service> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Service deleted")
        }
    }
}