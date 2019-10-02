package api.speaker

import model.speaker.RequestSpeaker
import repository.speakers.SpeakersRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val SPEAKERS = "/speakers"
@KtorExperimentalLocationsAPI
@Location(SPEAKERS)

class Speaker

@KtorExperimentalLocationsAPI
fun Route.speakers(db: SpeakersRepository) {
    authenticate("jwt") {
        get<Speaker> {
            val phrases = db.speakers()
            call.respond(phrases)
        }
        post<Speaker> {
            val request = call.receive<RequestSpeaker>()
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
                request.cost,
                request.numberVisit,
                request.image,
                request.company,
                request.rating,
                request.date)
            call.respond(HttpStatusCode.Created)
        }
        delete<Speaker> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Speaker deleted")
        }
    }
}