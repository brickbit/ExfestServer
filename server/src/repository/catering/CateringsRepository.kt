package repository.catering

import model.catering.Catering

interface CateringsRepository {
    suspend fun add(name: String,
                    cost: Float,
                    mandatory: Boolean,
                    rating: Int,
                    numberDiner: Int,
                    dateService: String,
                    dateRequest: String)
    suspend fun catering(id: Int): Catering?
    suspend fun catering(id: String): Catering?
    suspend fun caterings(): List<Catering>
    suspend fun update(
                    name: String,
                    cost: Float,
                    mandatory: Boolean,
                    rating: Int,
                    numberDiner: Int,
                    dateService: String,
                    dateRequest: String)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}