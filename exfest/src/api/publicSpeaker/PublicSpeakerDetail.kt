package api.publicSpeaker

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.publicSpeaker.PublicSpeakersRepository

@KtorExperimentalLocationsAPI
@Location("$PUBLIC_SPEAKERS/{id}")

data class PublicSpeakerDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.publicSpeakerDetail(db: PublicSpeakersRepository) {
    get<PublicSpeakerDetail> { item ->
        when (db.publicSpeakers().find { it.id == item.id }) {
            null -> call.respond(HttpStatusCode.NotFound, Error("Speaker with id ${item.id} not found"))
            else -> {
                call.respond(db.publicSpeaker(item.id)!!)
            }
        }
    }
}