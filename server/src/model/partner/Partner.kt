package model.partner

import model.merchandising.Merchandisings
import model.service.Services
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Partner(
    val id: Int,
    val name: String,
    val income: Float,
    val service: Int?,
    val image: String,
    val category: String): Serializable

object Partners: Table() {
    val id = Merchandisings.integer("id").primaryKey().autoIncrement()
    val name = varchar("name",128)
    val income = float("income")
    val service = reference("service", Services.id).nullable()
    val image = text("image")
    val category = varchar("category",255)
}