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

const val BILLS = "/bills"
@KtorExperimentalLocationsAPI
@Location(BILLS)

class Bill

@KtorExperimentalLocationsAPI
fun Route.bills(db: BillsRepository) {
    authenticate("jwt") {
        get<Bill> {
            val bills = db.bills()
            call.respond(bills)
        }
        post<Bill> {
            val request = call.receive<RequestBill>()
            db.add(
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
                request.year)
            call.respond(HttpStatusCode.Created)
        }
        delete<Bill> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Bill deleted")
        }
    }
}