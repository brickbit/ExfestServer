package api.rating

import model.rating.RequestRating
import repository.rating.RatingsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("$RATINGS/{id}")

data class RatingDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.ratingsDetail(db: RatingsRepository) {
    authenticate("jwt") {
        get<RatingDetail> { item ->
            when (db.ratings().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Rating with id ${item.id} not found"))
                else -> {
                    call.respond(db.rating(item.id)!!)
                }
            }
        }
        delete<RatingDetail> { item ->
            when (db.ratings().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Rating with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Rating $item.id deleted")
                }
            }
        }
        post<RatingDetail> { item ->
            val request = call.receive<RequestRating>()
            when (db.ratings().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Rating with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.rate,
                        request.opinion
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}