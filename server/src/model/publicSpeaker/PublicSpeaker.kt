package model.publicSpeaker

import org.jetbrains.exposed.sql.Table
import java.io.Serializable


data class PublicSpeaker(
    val id: Int,
    val name: String,
    val surname: String,
    val moreInfo: String,
    val image: String,
    val company: String,
    val email: String): Serializable

object PublicSpeakers: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val surname = varchar("surname", 255)
    val moreInfo = text("more_info")
    val image = text("image")
    val company = varchar("company",255)
    val email = varchar("email",64)
}