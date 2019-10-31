package api.publicOrganizer

import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.publicOrganizer.PublicOrganizersRepository

const val PUBLIC_ORGANIZER = "/publicOrganizers"
@KtorExperimentalLocationsAPI
@Location(PUBLIC_ORGANIZER)

class PublicOrganizer

@KtorExperimentalLocationsAPI
fun Route.publicOrganizers(db: PublicOrganizersRepository) {
    get<PublicOrganizer> {
        val attendees = db.publicOrganizers()
        call.respond(attendees)
    }
}