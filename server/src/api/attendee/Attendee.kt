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

const val ATTENDEES = "/attendees"
@KtorExperimentalLocationsAPI
@Location(ATTENDEES)

class Attendee

@KtorExperimentalLocationsAPI
fun Route.attendees(db: AttendeesRepository) {
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
                request.rating,
                request.dateRequestTicket,
                request.dateGrantTicket,
                request.datePayedTicket,
                request.timesExpiredTicket,
                request.timesAbsent)
            call.respond(HttpStatusCode.Created)
        }
        delete<Attendee> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Attendee deleted")
        }
    }
}