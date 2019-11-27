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

@KtorExperimentalLocationsAPI
@Location("$TRANSPORTS/{id}")

data class TransportsDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.transportsDetail(db: TransportsRepository) {
    authenticate("jwt") {
        get<TransportsDetail> { item ->
            when (db.transports().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Transport with id ${item.id} not found"))
                else -> {
                    call.respond(db.transport(item.id)!!)
                }
            }
        }
        delete<TransportsDetail> { item ->
            when (db.transports().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Transport with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Transport $item.id deleted")
                }
            }
        }
        post<TransportsDetail> { item ->
            val request = call.receive<RequestTransport>()
            when (db.transports().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Transport with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.name,
                        request.number,
                        request.cost,
                        request.shared,
                        request.dateRequest,
                        request.dateArrive,
                        request.dateExit
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}