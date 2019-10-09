package model.publicAttendee

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class PublicAttendee(
    val id: Int,
    val name: String,
    val surname: String,
    val company: String,
    val email: String): Serializable

object PublicAttendees: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val surname = varchar("surname", 255)
    val company = varchar("company",255)
    val email = varchar("email",64)
}