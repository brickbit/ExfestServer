package api.publicPartner

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.publicPartner.PublicPartnersRepository

@KtorExperimentalLocationsAPI
@Location("$PUBLIC_PARTNERS/{id}")

data class PartnerDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.publicPartnerDetail(db: PublicPartnersRepository) {
    get<PartnerDetail> { item ->
        when (db.publicPartners().find { it.id == item.id }) {
            null -> call.respond(HttpStatusCode.NotFound, Error("Partner with id ${item.id} not found"))
            else -> {
                call.respond(db.publicPartner(item.id)!!)
            }
        }
    }
}
