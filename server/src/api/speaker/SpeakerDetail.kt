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

@KtorExperimentalLocationsAPI
@Location("$SPEAKERS/{id}")

data class SpeakerDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.speakerDetail(db: SpeakersRepository, dbPublic: PublicSpeakersRepository) {
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
                    dbPublic.remove(item.id)
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
                        request.email
                    )
                    when (dbPublic.publicSpeakers().find { it.id == item.id }) {
                        null -> call.respond(
                            HttpStatusCode.NotFound,
                            Error("Public Speaker with id ${item.id} not found")
                        )
                        else -> {
                            val speaker = dbPublic.publicSpeakers().find { it.id == item.id }
                            dbPublic.update(
                                speaker!!.id,
                                request.name,
                                request.surname,
                                request.moreInfo,
                                request.image,
                                request.company,
                                request.email
                            )
                            call.respond(HttpStatusCode.Created)
                        }
                    }
                }
            }
        }
    }
}