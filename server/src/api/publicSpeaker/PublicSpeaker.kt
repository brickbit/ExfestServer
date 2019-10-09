package api.publicSpeaker

import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.publicSpeaker.PublicSpeakersRepository


const val PUBLIC_SPEAKERS = "/publicSpeakers"
@KtorExperimentalLocationsAPI
@Location(PUBLIC_SPEAKERS)

class PublicSpeaker

@KtorExperimentalLocationsAPI
fun Route.publicSpeakers(db: PublicSpeakersRepository) {
    get<PublicSpeaker> {
        val attendees = db.publicSpeakers()
        call.respond(attendees)
    }
}