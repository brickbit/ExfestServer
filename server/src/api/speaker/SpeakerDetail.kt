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

@KtorExperimentalLocationsAPI
@Location("$SPEAKERS/{id}")

data class SpeakerDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.speakerDetail(db: SpeakersRepository) {
    authenticate("jwt") {
        get<SpeakerDetail> { item ->
            when (db.speakers().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Speaker with id ${item.id} not found"))
                else -> {
                    call.respond(db.speaker(item.id)!!)
                }
            }
        }
        delete<SpeakerDetail> { item ->
            when (db.speakers().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Speaker with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Speaker $item.id deleted")
                }
            }
        }
        post<SpeakerDetail> { item ->
            val request = call.receive<RequestSpeaker>()
            when (db.speakers().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Speaker with id ${item.id} not found"))
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
                        request.cost,
                        request.numberVisit,
                        request.image,
                        request.company,
                        request.rating,
                        request.date
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}