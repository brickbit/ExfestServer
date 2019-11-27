package api.publicAttendee

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.publicAttendee.PublicAttendeesRepository

@KtorExperimentalLocationsAPI
@Location("$PUBLIC_ATTENDEES/{id}")

data class PublicAttendeeDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.publicAttendeeDetail(db: PublicAttendeesRepository) {
    get<PublicAttendeeDetail> { item ->
        when (db.publicAttendees().find { it.id == item.id }) {
            null -> call.respond(HttpStatusCode.NotFound, Error("Attendee with id ${item.id} not found"))
            else -> {
                call.respond(db.publicAttendee(item.id)!!)
            }
        }
    }
}