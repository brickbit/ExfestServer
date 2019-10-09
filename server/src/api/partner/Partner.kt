package api.partner

import model.partner.RequestPartner
import repository.partner.PartnersRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.publicPartner.PublicPartnersRepository

const val PARTNERS = "/partners"
@KtorExperimentalLocationsAPI
@Location(PARTNERS)

class Partner

@KtorExperimentalLocationsAPI
fun Route.partners(db: PartnersRepository, dbPublic: PublicPartnersRepository) {
    authenticate("jwt") {
        get<Partner> {
            val partners = db.partners()
            call.respond(partners)
        }
        post<Partner> {
            val request = call.receive<RequestPartner>()
            db.add(
                request.name,
                request.income,
                request.service,
                request.image,
                request.category,
                request.email
            )
            when (db.partners().find { it.email == request.email }) {
                null -> call.respond(
                    HttpStatusCode.NotFound,
                    Error("Public partner could not be created")
                )
                else -> {
                    val partner = db.partners().find { it.email == request.email }
                    dbPublic.add(
                        partner!!.id,
                        request.name,
                        request.image,
                        request.category,
                        request.email
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }

        }
        delete<Partner> {
            dbPublic.clear()
            db.clear()
            call.respond(HttpStatusCode.OK, "Partner deleted")
        }
    }
}