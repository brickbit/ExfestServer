package api.merchandising

import model.merchandising.RequestMerchandising
import repository.merchandising.MerchandisingsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("$MERCHANDISINGS/{id}")

data class MerchandisingDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.merchandisingsDetail(db: MerchandisingsRepository) {
    authenticate("jwt") {
        get<MerchandisingDetail> { item ->
            when (db.merchandisings().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Merchandising with id ${item.id} not found"))
                else -> {
                    call.respond(db.merchandising(item.id)!!)
                }
            }
        }
        delete<MerchandisingDetail> { item ->
            when (db.merchandisings().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Merchandising with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Merchandising $item.id deleted")
                }
            }
        }
        post<MerchandisingDetail> { item ->
            val request = call.receive<RequestMerchandising>()
            when(db.merchandisings().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Merchandising with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.name,
                        request.size,
                        request.cost,
                        request.genre,
                        request.customized)
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}