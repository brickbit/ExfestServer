package model.topic

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Topic(val id: Int,
                 val name: String,
                 val image: String): Serializable

object Topics: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name",128)
    val image = text("image")
}