package api.hotel

import model.hotel.RequestHotel
import repository.hotel.HotelsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("$HOTELS/{id}")

data class HotelDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.hotelsDetail(db: HotelsRepository) {
    authenticate("jwt") {
        get<HotelDetail> { item ->
            when (db.hotels().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Hotel with id ${item.id} not found"))
                else -> {
                    call.respond(db.hotel(item.id)!!)
                }
            }
        }
        delete<HotelDetail> { item ->
            when (db.hotels().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Hotel with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Hotel $item.id deleted")
                }
            }
        }
        post<HotelDetail> { item->
            val request = call.receive<RequestHotel>()
            when(db.hotels().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Hotel with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.dateArrival,
                        request.dateExit,
                        request.dateBooking,
                        request.cost,
                        request.distance,
                        request.transport,
                        request.hotelPayed,
                        request.userPayed,
                        request.rating
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}