package api.publicAttendee

import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.publicAttendee.PublicAttendeesRepository

const val PUBLIC_ATTENDEES = "/publicAttendees"
@KtorExperimentalLocationsAPI
@Location(PUBLIC_ATTENDEES)

class PublicAttendee

@KtorExperimentalLocationsAPI
fun Route.publicAttendees(db: PublicAttendeesRepository) {
    get<PublicAttendee> {
        val attendees = db.publicAttendees()
        call.respond(attendees)
    }
}