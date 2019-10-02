package model.rating

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Rating(val id: Int,
                 val rate: Int,
                 val opinion: String): Serializable

object Ratings: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val rate: Column<Int> = integer("rate")
    val opinion: Column<String> = text("opinion")
}