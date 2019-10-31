package model.service

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Service(
    val id: Int,
    val name: String,
    val cost: Float,
    val description: String,
    val granted: Boolean,
    val company: String
): Serializable

object Services: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name",128)
    val cost = float("cost")
    val description = text("description")
    val granted = bool("granted")
    val company = varchar("company",128)
}