package api.publicOrganizer

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.publicOrganizer.PublicOrganizersRepository

@KtorExperimentalLocationsAPI
@Location("$PUBLIC_ORGANIZER/{id}")

data class PublicOrganizerDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.publicOrganizerDetail(db: PublicOrganizersRepository) {
    get<PublicOrganizerDetail> { item ->
        when (db.publicOrganizers().find { it.id == item.id }) {
            null -> call.respond(HttpStatusCode.NotFound, Error("Organizer with id ${item.id} not found"))
            else -> {
                call.respond(db.publicOrganizer(item.id)!!)
            }
        }
    }
}