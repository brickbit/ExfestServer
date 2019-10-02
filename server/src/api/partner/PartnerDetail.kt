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

@KtorExperimentalLocationsAPI
@Location("$PARTNERS/{id}")

data class PartnerDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.partnersDetail(db: PartnersRepository) {
    authenticate("jwt") {
        get<PartnerDetail> { item ->
            when (db.partners().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Partner with id ${item.id} not found"))
                else -> {
                    call.respond(db.partner(item.id)!!)
                }
            }
        }
        delete<PartnerDetail> { item ->
            when (db.partners().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Partner with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Partner $item.id deleted")
                }
            }
        }
        post<PartnerDetail> { item ->
            val request = call.receive<RequestPartner>()
            when(db.partners().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Partner with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.name,
                        request.income,
                        request.service,
                        request.image,
                        request.category
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}