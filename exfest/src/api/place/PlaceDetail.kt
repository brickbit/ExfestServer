package api.place

import model.place.RequestPlace
import repository.place.PlacesRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("$PLACES/{id}")

data class PlaceDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.placeDetail(db: PlacesRepository) {
    authenticate("jwt") {
        get<PlaceDetail> { item ->
            when (db.places().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Place with id ${item.id} not found"))
                else -> {
                    call.respond(db.place(item.id)!!)
                }
            }
        }
        delete<PlaceDetail> { item ->
            when (db.places().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Place with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Place $item.id deleted")
                }
            }
        }
        post<PlaceDetail> { item ->
            val request = call.receive<RequestPlace>()
            when(db.places().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Place with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.day,
                        request.cost,
                        request.xCoordinate,
                        request.yCoordinate,
                        request.description,
                        request.indication,
                        request.image,
                        request.mandatoryCatering
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}