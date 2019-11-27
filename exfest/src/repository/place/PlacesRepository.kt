package repository.place

import model.place.Place

interface PlacesRepository {
    suspend fun add(day: String,
                    cost: Float,
                    xCoordinate: Float,
                    yCoordinate: Float,
                    description: String,
                    indication: String,
                    image: String,
                    mandatoryCatering: Boolean)
    suspend fun place(id: Int): Place?
    suspend fun place(id: String): Place?
    suspend fun places(): List<Place>
    suspend fun update(
                    day: String,
                    cost: Float,
                    xCoordinate: Float,
                    yCoordinate: Float,
                    description: String,
                    indication: String,
                    image: String,
                    mandatoryCatering: Boolean)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}