package model.foodRestriction

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class FoodRestriction(val id: Int,
                  val name: String,
                  val number: Int): Serializable

object FoodRestrictions: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name",64)
    val number = integer("number")
}