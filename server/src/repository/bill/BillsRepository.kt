package repository.bill

import model.bill.Bill

interface BillsRepository {
    suspend fun add(speakerCost: Float,
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
                    year: Int)
    suspend fun bill(id: Int): Bill?
    suspend fun bill(id: String): Bill?
    suspend fun bills(): List<Bill>
    suspend fun update(
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
                    year: Int)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}