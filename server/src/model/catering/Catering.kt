package model.catering

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Catering(
    val id: Int,
    val name: String,
    val cost: Float,
    val mandatory: Boolean,
    val numberDiner: Int,
    val dateService: String,
    val dateRequest: String): Serializable

object Caterings: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name",128)
    val cost = float("cost")
    val mandatory = bool("mandatory")
    val numberDiner = integer("number_diner")
    val dateService = varchar("date_service",32)
    val dateRequest = varchar("date_request",32)
}