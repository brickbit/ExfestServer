package api.voluntary

import model.voluntary.RequestVoluntary
import repository.voluntary.VoluntariesRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("$VOLUNTARIES/{id}")

data class VoluntaryDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.voluntariesDetail(db: VoluntariesRepository) {
    authenticate("jwt") {
        get<VoluntaryDetail> { item ->
            when (db.voluntaries().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Agenda with id ${item.id} not found"))
                else -> {
                    call.respond(db.voluntary(item.id)!!)
                }
            }
        }
        delete<VoluntaryDetail> { item ->
            when (db.voluntaries().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Voluntary with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Voluntary $item.id deleted")
                }
            }
        }
        post<VoluntaryDetail> { item ->
            val request = call.receive<RequestVoluntary>()
            when (db.voluntaries().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Voluntary with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.name,
                        request.surname,
                        request.genre,
                        request.transport,
                        request.foodRestriction,
                        request.merchandising,
                        request.moreInfo,
                        request.children,
                        request.comeChildren,
                        request.nChildren,
                        request.ageChildren,
                        request.hotel,
                        request.priceTicket,
                        request.numberVisit,
                        request.image,
                        request.company,
                        request.dateRequestTicket,
                        request.dateGrantTicket,
                        request.datePayedTicket,
                        request.timesExpiredTicket,
                        request.timesAbsent,
                        request.gdg,
                        request.cost
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}