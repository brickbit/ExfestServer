package api.catering

import model.catering.RequestCatering
import repository.catering.CateringsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("$CATERINGS/{id}")

data class CateringDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.cateringsDetail(db: CateringsRepository) {
    authenticate("jwt") {
        get<CateringDetail> { item ->
            when (db.caterings().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Catering with id ${item.id} not found"))
                else -> {
                    call.respond(db.catering(item.id)!!)
                }
            }
        }
        delete<CateringDetail> { item ->
            when (db.caterings().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Catering with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Bill $item.id deleted")
                }
            }
        }

        post<CateringDetail> { item ->
            val request = call.receive<RequestCatering>()
            when(db.caterings().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Catering with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.name,
                        request.cost,
                        request.mandatory,
                        request.foodRestriction,
                        request.dateService,
                        request.dateRequest
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}