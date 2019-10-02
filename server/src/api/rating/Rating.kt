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

const val RATINGS = "/ratings"
@KtorExperimentalLocationsAPI
@Location(RATINGS)

class Rating

@KtorExperimentalLocationsAPI
fun Route.ratings(db: RatingsRepository) {
    get<Rating> {
        val phrases = db.ratings()
        call.respond(phrases)
    }
    authenticate("jwt") {
        post<Rating> {
            val request = call.receive<RequestRating>()
            db.add(
                request.rate,
                request.opinion)
            call.respond(HttpStatusCode.Created)
        }
        delete<Rating> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Rating deleted")
        }
    }
}