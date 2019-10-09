package model.publicVoluntary

import org.jetbrains.exposed.sql.Table
import java.io.Serializable


data class PublicVoluntary(
    val id: Int,
    val name: String,
    val surname: String,
    val image: String,
    val company: String,
    val gdg: String,
    val email: String): Serializable

object PublicVoluntaries: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val surname = varchar("surname", 255)
    val image = text("image")
    val company = varchar("company",255)
    val gdg = varchar("gdg", 128)
    val email = varchar("email", 64)
}