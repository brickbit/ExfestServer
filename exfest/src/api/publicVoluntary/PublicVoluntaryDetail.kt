package api.publicVoluntary

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.publicVoluntary.PublicVoluntariesRepository

@KtorExperimentalLocationsAPI
@Location("$PUBLIC_VOLUNTARY/{id}")

data class PublicVoluntaryDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.publicVoluntaryDetail(db: PublicVoluntariesRepository) {
    get<PublicVoluntaryDetail> { item ->
        when (db.publicVoluntaries().find { it.id == item.id }) {
            null -> call.respond(HttpStatusCode.NotFound, Error("Voluntary with id ${item.id} not found"))
            else -> {
                call.respond(db.publicVoluntary(item.id)!!)
            }
        }
    }
}