package api.foodRestriction

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import model.foodRestriction.RequestFoodRestriction
import repository.foodRestriction.FoodRestrictionRepository


const val FOODRESTRICTION = "/foodRestrictions"
@KtorExperimentalLocationsAPI
@Location(FOODRESTRICTION)

class FoodRestriction

@KtorExperimentalLocationsAPI
fun Route.foodRestrictions(db: FoodRestrictionRepository) {
    authenticate("jwt") {
        get<FoodRestriction> {
            val phrases = db.foodRestrictions()
            call.respond(phrases)
        }
        post<FoodRestriction> {
            val request = call.receive<RequestFoodRestriction>()
            db.add(
                request.name,
                request.idAttendee,
                request.idOrganizer,
                request.idSpeaker,
                request.idVoluntary)
            call.respond(HttpStatusCode.Created)
        }
        delete<FoodRestriction> {
            db.clear()
            call.respond(HttpStatusCode.OK, "FoodRestriction deleted")
        }
    }
}