package repository.hotel

import model.hotel.Hotel

interface HotelsRepository {
    suspend fun add(name: String,
                    dayArrival: String,
                    dayExit: String,
                    dateBooking: String,
                    cost: Float,
                    distance: Float,
                    transport: String,
                    hotelPayed: Boolean,
                    userPayed: Boolean)
    suspend fun hotel(id: Int): Hotel?
    suspend fun hotel(id: String): Hotel?
    suspend fun hotels(): List<Hotel>
    suspend fun update(
                    name: String,
                    dayArrival: String,
                    dayExit: String,
                    dateBooking: String,
                    cost: Float,
                    distance: Float,
                    transport: String,
                    hotelPayed: Boolean,
                    userPayed: Boolean)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}