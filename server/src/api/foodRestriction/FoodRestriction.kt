package api.foodRestriction

import model.foodRestriction.RequestFoodRestriction
import repository.foodRestriction.FoodRestrictionsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val FOOD_RESTRICTIONS = "/foodRestrictions"
@KtorExperimentalLocationsAPI
@Location(FOOD_RESTRICTIONS)

class FoodRestriction

@KtorExperimentalLocationsAPI
fun Route.foodRestrictions(db: FoodRestrictionsRepository) {
    get<FoodRestriction> {
        val phrases = db.foodRestrictions()
        call.respond(phrases)
    }
    authenticate("jwt") {
        post<FoodRestriction> {
            val request = call.receive<RequestFoodRestriction>()
            db.add(
                request.name,
                request.number)
            call.respond(HttpStatusCode.Created)
        }
        delete<FoodRestriction> {
            db.clear()
            call.respond(HttpStatusCode.OK, "FoodRestriction deleted")
        }
    }
}