package model.publicPartner

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class PublicPartner(
    val id: Int,
    val name: String,
    val image: String,
    val category: String,
    val email: String): Serializable

object PublicPartners: Table() {
    val id = integer("id").primaryKey()
    val name = varchar("name",128)
    val image = text("image")
    val category = varchar("category",255)
    val email = varchar("email",64)
}