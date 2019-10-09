package api.transport

import model.transport.RequestTransport
import repository.transport.TransportsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val TRANSPORTS = "/transports"
@KtorExperimentalLocationsAPI
@Location(TRANSPORTS)

class Transport

@KtorExperimentalLocationsAPI
fun Route.transports(db: TransportsRepository) {
    authenticate("jwt") {
        get<Transport> {
            val transports = db.transports()
            call.respond(transports)
        }
        post<Transport> {
            val request = call.receive<RequestTransport>()
            db.add(
                request.kind,
                request.cost,
                request.shared,
                request.dateRequest,
                request.dateArrive,
                request.dateExit)
            call.respond(HttpStatusCode.Created)
        }
        delete<Transport> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Transport deleted")
        }
    }
}