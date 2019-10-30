package model.hotel

data class RequestHotel(
    val name: String,
                 val dateArrival: String,
                 val dateExit: String,
                 val dateBooking: String,
                 val cost: Float,
                 val distance: Float,
                 val transport: String,
                 val speaker: Int,
                 val voluntary: Int,
                 val hotelPayed: Boolean,
                 val userPayed: Boolean,
                 val rating: Int,
                 val foodRestriction: Int)