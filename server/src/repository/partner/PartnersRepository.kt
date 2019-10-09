package repository.partner

import model.partner.Partner

interface PartnersRepository {
    suspend fun add(name: String,
                    income: Float,
                    service: Int,
                    image: String,
                    category: String,
                    email: String)
    suspend fun partner(id: Int): Partner?
    suspend fun partner(id: String): Partner?
    suspend fun partners(): List<Partner>
    suspend fun update(
                    name: String,
                    income: Float,
                    service: Int,
                    image: String,
                    category: String,
                    email: String)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}