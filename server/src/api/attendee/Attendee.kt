package api.attendee

import model.attendee.RequestAttendee
import repository.attendee.AttendeesRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import model.Error
import repository.publicAttendee.PublicAttendeesRepository

const val ATTENDEES = "/attendees"
@KtorExperimentalLocationsAPI
@Location(ATTENDEES)

class Attendee

@KtorExperimentalLocationsAPI
fun Route.attendees(db: AttendeesRepository, dbPublic: PublicAttendeesRepository) {
    authenticate("jwt") {
        get<Attendee> {
            val attendees = db.attendees()
            call.respond(attendees)
        }
        post<Attendee> {
            val request = call.receive<RequestAttendee>()
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
                request.rating,
                request.dateRequestTicket,
                request.dateGrantTicket,
                request.datePayedTicket,
                request.timesExpiredTicket,
                request.timesAbsent,
                request.email)
            when (db.attendees().find { it.email == request.email }) {
                null -> call.respond(HttpStatusCode.NotFound,
                    call.respond(Error("Public attendee could not be created")))
                else -> {
                    if (db.attendees().count { it.email == request.email } > 1) {
                        call.respond(Error("Email exist, email can be repeated"))
                    } else {
                        val attendee = db.attendees().find { it.email == request.email }
                        dbPublic.add(
                            attendee!!.id,
                            request.name,
                            request.surname,
                            request.company,
                            request.email
                        )
                        call.respond(HttpStatusCode.Created)
                    }
                }
            }
        }
        delete<Attendee> {
            db.clear()
            dbPublic.clear()
            call.respond(HttpStatusCode.OK, "Attendee deleted")
        }
    }
}