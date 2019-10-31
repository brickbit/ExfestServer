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
import repository.publicSpeaker.PublicSpeakersRepository

const val SPEAKERS = "/speakers"
@KtorExperimentalLocationsAPI
@Location(SPEAKERS)

class Speaker

@KtorExperimentalLocationsAPI
fun Route.speakers(db: SpeakersRepository, dbPublic: PublicSpeakersRepository) {
    authenticate("jwt") {
        get<Speaker> {
            val speakers = db.speakers()
            call.respond(speakers)
        }
        post<Speaker> {
            val request = call.receive<RequestSpeaker>()
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
                request.cost,
                request.numberVisit,
                request.image,
                request.company,
                request.year,
                request.email)
            when (db.speakers().find { it.email == request.email }) {
                null -> call.respond(HttpStatusCode.NotFound,
                    Error("Public attendee could not be created"))
                else -> {
                    val speaker = db.speakers().find { it.email == request.email }
                    dbPublic.add(
                        speaker!!.id,
                        request.name,
                        request.surname,
                        request.moreInfo,
                        request.image,
                        request.company,
                        request.email)
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
        delete<Speaker> {
            db.clear()
            dbPublic.clear()
            call.respond(HttpStatusCode.OK, "Attendee deleted")
        }
    }
}