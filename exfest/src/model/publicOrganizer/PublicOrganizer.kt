package model.publicOrganizer

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class PublicOrganizer(
    val id: Int,
    val name: String,
    val surname: String,
    val moreInfo: String,
    val image: String,
    val company: String,
    val email: String,
    val gdg: String): Serializable

object PublicOrganizers: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val surname = varchar("surname", 255)
    val moreInfo = text("more_info")
    val image = text("image")
    val company = varchar("company",255)
    val gdg = varchar("gdg", 128)
    val email = varchar("email",128)
}