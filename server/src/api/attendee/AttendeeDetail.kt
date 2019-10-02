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

@KtorExperimentalLocationsAPI
@Location("$ATTENDEES/{id}")

data class AttendeeDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.attendeesDetail(db: AttendeesRepository) {
    authenticate("jwt") {
        get<AttendeeDetail> { item ->
            when (db.attendees().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Agenda with id ${item.id} not found"))
                else -> {
                    call.respond(db.attendee(item.id)!!)
                }
            }
        }
        delete<AttendeeDetail> { item ->
            when (db.attendees().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Attendee with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Attendee $item.id deleted")
                }
            }
        }
        post<AttendeeDetail> { item ->
            val request = call.receive<RequestAttendee>()
            when(db.attendees().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Attendee with id ${item.id} not found"))
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
                        request.rating,
                        request.dateRequestTicket,
                        request.dateGrantTicket,
                        request.datePayedTicket,
                        request.timesExpiredTicket,
                        request.timesAbsent
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}