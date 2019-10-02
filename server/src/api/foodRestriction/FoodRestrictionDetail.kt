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

@KtorExperimentalLocationsAPI
@Location("$FOOD_RESTRICTIONS/{id}")

data class FoodRestrictionDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.foodRestrictionsDetail(db: FoodRestrictionsRepository) {
    get<FoodRestrictionDetail> { item ->
        when (db.foodRestrictions().find { it.id == item.id } ) {
            null -> call.respond(HttpStatusCode.NotFound, Error("FoodRestriction with id ${item.id} not found"))
            else -> {
                call.respond(db.foodRestriction(item.id)!!)
            }
        }
    }
    authenticate("jwt") {
        delete<FoodRestrictionDetail> { item ->
            when (db.foodRestrictions().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("FoodRestriction with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "FoodRestriction $item.id deleted")
                }
            }
        }
        post<FoodRestrictionDetail> { item ->
            val request = call.receive<RequestFoodRestriction>()
            when (db.foodRestrictions().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("FoodRestriction with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.name,
                        request.number
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}