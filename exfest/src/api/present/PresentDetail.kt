package api.present

import model.present.RequestPresent
import repository.present.PresentsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("$PRESENTS/{id}")

data class PresentDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.presentsDetail(db: PresentsRepository) {
    authenticate("jwt") {
        get<PresentDetail> { item ->
            when (db.presents().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Present with id ${item.id} not found"))
                else -> {
                    call.respond(db.present(item.id)!!)
                }
            }
        }
        delete<PresentDetail> { item ->
            when (db.presents().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Present with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Present $item.id deleted")
                }
            }
        }
        post<PresentDetail> { item ->
            val request = call.receive<RequestPresent>()
            when(db.presents().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Present with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.name,
                        request.cost,
                        request.description,
                        request.speaker,
                        request.voluntary,
                        request.attendee,
                        request.granted,
                        request.partner
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}