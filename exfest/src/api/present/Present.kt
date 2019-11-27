package api.present

import model.present.RequestPresent
import repository.present.PresentsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val PRESENTS = "/presents"
@KtorExperimentalLocationsAPI
@Location(PRESENTS)

class Present

@KtorExperimentalLocationsAPI
fun Route.presents(db: PresentsRepository) {
    authenticate("jwt") {
        get<Present> {
            val phrases = db.presents()
            call.respond(phrases)
        }
        post<Present> {
            val request = call.receive<RequestPresent>()
            db.add(
                request.name,
                request.cost,
                request.description,
                request.speaker,
                request.voluntary,
                request.attendee,
                request.granted,
                request.partner)
            call.respond(HttpStatusCode.Created)
        }
        delete<Present> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Present deleted")
        }
    }
}