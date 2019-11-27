package model.place

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Place(val id: Int,
                 val day: String,
                 val cost: Float,
                 val xCoordinate: Float,
                 val yCoordinate: Float,
                 val description: String,
                 val indication: String,
                 val image: String,
                 val mandatoryCatering: Boolean): Serializable

object Places: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val day = varchar("day",255)
    val cost = float("cost")
    val xCoordinate = float("x_coordinate")
    val yCoordinate = float("y_coordinate")
    val description = text("description")
    val indication = text("indication")
    val image = varchar("image",512)
    val mandatoryCatering = bool("mandatory_catering")
}