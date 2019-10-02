package repository.bill

import model.bill.Bill
import model.bill.Bills
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class BillsImplementation : BillsRepository {
    private fun toBill(row: ResultRow): Bill =
        Bill(
            id = row[Bills.id],
            speakerCost =  row[Bills.speakerCost],
            placeCost = row[Bills.placeCost],
            hotelCost = row[Bills.hotelCost],
            transportCost = row[Bills.transportCost],
            merchandisingCost = row[Bills.merchandisingCost],
            cateringCost = row[Bills.cateringCost],
            serviceCost = row[Bills.serviceCost],
            presentCost = row[Bills.presentCost],
            totalCost = row[Bills.totalCost],
            ticketsIncome = row[Bills.ticketsIncome],
            partnersIncome = row[Bills.partnersIncome],
            totalIncome = row[Bills.totalIncome],
            debt = row[Bills.debt],
            result = row[Bills.result],
            year = row[Bills.year]
        )

    override suspend fun add(
        speakerCost: Float,
        placeCost: Float,
        hotelCost: Float,
        transportCost: Float,
        merchandisingCost: Float,
        cateringCost: Float,
        serviceCost: Float,
        presentCost: Float,
        totalCost: Float,
        ticketsIncome: Float,
        partnersIncome: Float,
        totalIncome: Float,
        debt: Float,
        result: Float,
        year: Int
    ) {
        transaction {
            Bills.insert {
                it [this.speakerCost] = speakerCost
                it [this.placeCost] = placeCost
                it [this.hotelCost] = hotelCost
                it [this.transportCost] = transportCost
                it [this.merchandisingCost] = merchandisingCost
                it [this.cateringCost] = cateringCost
                it [this.serviceCost] =  serviceCost
                it [this.presentCost] =  presentCost
                it [this.totalCost] =  totalCost
                it [this.ticketsIncome] =  ticketsIncome
                it [this.partnersIncome] =  partnersIncome
                it [this.totalIncome] =  totalIncome
                it [this.debt] =  debt
                it [this.result] =  result
                it [this.year] =  year
            }
        }
    }

    override suspend fun bill(id: Int): Bill? = dbQuery {
            Bills.select {
                (Bills.id eq id)
            }.mapNotNull { toBill(it) }.singleOrNull()
    }

    override suspend fun bill(id: String): Bill? {
        return bill(id.toInt())
    }

    override suspend fun bills(): List<Bill>  = dbQuery {
        Bills.selectAll().map { toBill(it) }
    }

    override suspend fun update(
        speakerCost: Float,
        placeCost: Float,
        hotelCost: Float,
        transportCost: Float,
        merchandisingCost: Float,
        cateringCost: Float,
        serviceCost: Float,
        presentCost: Float,
        totalCost: Float,
        ticketsIncome: Float,
        partnersIncome: Float,
        totalIncome: Float,
        debt: Float,
        result: Float,
        year: Int
    ) {
        transaction {
            Bills.update {
                it [this.speakerCost] = speakerCost
                it [this.placeCost] = placeCost
                it [this.hotelCost] = hotelCost
                it [this.transportCost] = transportCost
                it [this.merchandisingCost] = merchandisingCost
                it [this.cateringCost] = cateringCost
                it [this.serviceCost] =  serviceCost
                it [this.presentCost] =  presentCost
                it [this.totalCost] =  totalCost
                it [this.ticketsIncome] =  ticketsIncome
                it [this.partnersIncome] =  partnersIncome
                it [this.totalIncome] =  totalIncome
                it [this.debt] =  debt
                it [this.result] =  result
                it [this.year] =  year
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (bill(id) == null) {
            throw  IllegalArgumentException("No agenda found for id ${bill(id)}")
        }
        return dbQuery {
            Bills.deleteWhere { Bills.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        bills().map { remove(it.id) }
    }

}