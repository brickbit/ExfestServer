package api.organizer

import model.organizer.RequestOrganizer
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.organizer.OrganizersRepository

@KtorExperimentalLocationsAPI
@Location("$ORGANIZERS/{id}")

data class OrganizerDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.organizersDetail(db: OrganizersRepository) {
    authenticate("jwt") {
        get<OrganizerDetail> { item ->
            when (db.organizers().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Organizer with id ${item.id} not found"))
                else -> {
                    call.respond(db.organizer(item.id)!!)
                }
            }
        }
        delete<OrganizerDetail> { item ->
            when (db.organizers().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Organizer with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Organizer $item.id deleted")
                }
            }
        }
        post<OrganizerDetail> { item ->
            val request = call.receive<RequestOrganizer>()
            when(db.organizers().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Organizer with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.name,
                        request.surname,
                        request.foodRestriction,
                        request.email,
                        request.password,
                        request.moreInfo,
                        request.image,
                        request.company,
                        request.gdg
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}