package api.publicPartner

import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.publicPartner.PublicPartnersRepository

const val PUBLIC_PARTNERS = "/publicPartners"
@KtorExperimentalLocationsAPI
@Location(PUBLIC_PARTNERS)

class PublicPartner

@KtorExperimentalLocationsAPI
fun Route.publicPartners(db: PublicPartnersRepository) {
    get<PublicPartner> {
        val attendees = db.publicPartners()
        call.respond(attendees)
    }
}