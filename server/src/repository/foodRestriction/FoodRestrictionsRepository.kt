package repository.foodRestriction

import model.foodRestriction.FoodRestriction

interface FoodRestrictionsRepository {
    suspend fun add(name: String,
                    number: Int)
    suspend fun foodRestriction(id: Int): FoodRestriction?
    suspend fun foodRestriction(id: String): FoodRestriction?
    suspend fun foodRestrictions(): List<FoodRestriction>
    suspend fun update(
                    name: String,
                    number: Int)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}