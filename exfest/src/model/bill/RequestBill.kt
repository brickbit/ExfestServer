package model.bill

data class RequestBill(
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
                val year: Int)