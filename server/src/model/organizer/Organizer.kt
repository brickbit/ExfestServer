package model.organizer

import io.ktor.auth.Principal
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Organizer(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String): Serializable, Principal

object Organizers: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val surname = varchar("surname", 255)
    val email = varchar("email",128)
    val password = varchar("password", 64)
}