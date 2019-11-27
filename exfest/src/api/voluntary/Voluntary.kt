package api.voluntary

import model.voluntary.RequestVoluntary
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import model.Error
import repository.publicVoluntary.PublicVoluntariesRepository
import repository.voluntary.VoluntariesRepository

const val VOLUNTARIES = "/voluntaries"
@KtorExperimentalLocationsAPI
@Location(VOLUNTARIES)

class Voluntary

@KtorExperimentalLocationsAPI
fun Route.voluntaries(db: VoluntariesRepository, dbPublic: PublicVoluntariesRepository) {
    authenticate("jwt") {
        get<Voluntary> {
            val attendees = db.voluntaries()
            call.respond(attendees)
        }
        post<Voluntary> {
            val request = call.receive<RequestVoluntary>()
            db.add(
                request.name,
                request.surname,
                request.foodRestriction,
                request.genre,
                request.transport,
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
                request.cost,
                request.email)
            when (db.voluntaries().find { it.email == request.email }) {
                null -> call.respond(HttpStatusCode.NotFound,
                    Error("Public attendee could not be created"))
                else -> {
                    if (db.voluntaries().count { it.email == request.email } > 1) {
                        call.respond(Error("Email exist, email can be repeated"))
                    } else {
                        val attendee = db.voluntaries().find { it.email == request.email }
                        dbPublic.add(
                            attendee!!.id,
                            request.name,
                            request.surname,
                            request.image,
                            request.company,
                            request.gdg,
                            request.email
                        )
                        call.respond(HttpStatusCode.Created)
                    }
                }
            }
        }
        delete<Voluntary> {
            db.clear()
            dbPublic.clear()
            call.respond(HttpStatusCode.OK, "Attendee deleted")
        }
    }
}