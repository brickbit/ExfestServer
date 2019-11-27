package api.bill

import model.bill.RequestBill
import repository.bill.BillsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("$BILLS/{id}")

data class BillDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.billsDetail(db: BillsRepository) {
    authenticate("jwt") {
        get<BillDetail> { item ->
            when (db.bills().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Catering with id ${item.id} not found"))
                else -> {
                    call.respond(db.bill(item.id)!!)
                }
            }
        }
        delete<BillDetail> { item ->
            when (db.bills().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Bill with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Bill $item.id deleted")
                }
            }
        }
        post<BillDetail> { item ->
            val request = call.receive<RequestBill>()
            when (db.bills().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Bill with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.speakerCost,
                        request.placeCost,
                        request.hotelCost,
                        request.transportCost,
                        request.merchandisingCost,
                        request.cateringCost,
                        request.serviceCost,
                        request.presentCost,
                        request.totalCost,
                        request.ticketsIncome,
                        request.partnersIncome,
                        request.totalIncome,
                        request.debt,
                        request.result,
                        request.year
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}