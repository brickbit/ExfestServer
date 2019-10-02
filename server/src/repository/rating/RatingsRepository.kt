package repository.rating

import model.rating.Rating

interface RatingsRepository {
    suspend fun add(rate: Int,
                    opinion: String)
    suspend fun rating(id: Int): Rating?
    suspend fun rating(id: String): Rating?
    suspend fun ratings(): List<Rating>
    suspend fun update(id: Int,
                    opinion: String)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}