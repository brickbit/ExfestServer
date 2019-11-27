package api.publicVoluntary

import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.publicVoluntary.PublicVoluntariesRepository

const val PUBLIC_VOLUNTARY = "/publicVoluntary"
@KtorExperimentalLocationsAPI
@Location(PUBLIC_VOLUNTARY)

class PublicVoluntary

@KtorExperimentalLocationsAPI
fun Route.publicVoluntary(db: PublicVoluntariesRepository) {
    get<PublicVoluntary> {
        val attendees = db.publicVoluntaries()
        call.respond(attendees)
    }
}