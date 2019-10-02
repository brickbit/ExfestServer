package repository.hotel

import model.hotel.Hotel

interface HotelsRepository {
    suspend fun add(dayArrival: String,
                    dayExit: String,
                    dateBooking: String,
                    cost: Float,
                    distance: Float,
                    transport: String,
                    hotelPayed: Boolean,
                    userPayed: Boolean,
                    rating: Int)
    suspend fun hotel(id: Int): Hotel?
    suspend fun hotel(id: String): Hotel?
    suspend fun hotels(): List<Hotel>
    suspend fun update(
                    dayArrival: String,
                    dayExit: String,
                    dateBooking: String,
                    cost: Float,
                    distance: Float,
                    transport: String,
                    hotelPayed: Boolean,
                    userPayed: Boolean,
                    rating: Int)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}