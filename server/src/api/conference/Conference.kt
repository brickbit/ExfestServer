package api.conference

import model.conference.RequestConference
import repository.conferences.ConferencesRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val CONFERENCES = "/conferences"
@KtorExperimentalLocationsAPI
@Location(CONFERENCES)

class Conference

@KtorExperimentalLocationsAPI
fun Route.conferences(db: ConferencesRepository) {
    authenticate("jwt") {
        get<Conference> {
            val phrases = db.conferences()
            call.respond(phrases)
        }
        post<Conference> {
            val request = call.receive<RequestConference>()
            db.add(
                request.title,
                request.hour,
                request.duration,
                request.speaker,
                request.topic,
                request.description,
                request.streaming,
                request.video)
            call.respond(HttpStatusCode.Created)
        }
        delete<Conference> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Conference deleted")
        }
    }
}