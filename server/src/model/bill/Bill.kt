package model.bill

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Bill(val id: Int,
                val speakerCost: Float,
                val placeCost: Float,
                val hotelCost: Float,
                val transportCost: Float,
                val merchandisingCost: Float,
                val cateringCost: Float,
                val serviceCost: Float,
                val presentCost: Float,
                val totalCost: Float,
                val ticketsIncome: Float,
                val partnersIncome: Float,
                val totalIncome: Float,
                val debt: Float,
                val result: Float,
                val year: Int): Serializable

object Bills: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val speakerCost = float("speaker_cost")
    val placeCost = float("place_cost")
    val hotelCost = float("hotel_cost")
    val transportCost = float("transport_cost")
    val merchandisingCost = float("merchandising_cost")
    val cateringCost = float("catering_cost")
    val serviceCost = float("service_cost")
    val presentCost = float("present_cost")
    val totalCost = float("total_cost")
    val ticketsIncome = float("ticket_income")
    val partnersIncome = float("partners_income")
    val totalIncome = float("total_income")
    val debt = float("debt")
    val result = float("result")
    val year = integer("year")
}