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

const val VOLUNTARIES = "/voluntaries"
@KtorExperimentalLocationsAPI
@Location(VOLUNTARIES)

class Voluntary

@KtorExperimentalLocationsAPI
fun Route.voluntaries(db: VoluntariesRepository) {
    authenticate("jwt") {
        get<Voluntary> {
            val voluntaries = db.voluntaries()
            call.respond(voluntaries)
        }
        post<Voluntary> {
            val request = call.receive<RequestVoluntary>()
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
                request.dateRequestTicket,
                request.dateGrantTicket,
                request.datePayedTicket,
                request.timesExpiredTicket,
                request.timesAbsent,
                request.gdg,
                request.cost)
            call.respond(HttpStatusCode.Created)
        }
        delete<Voluntary> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Voluntary deleted")
        }
    }
}