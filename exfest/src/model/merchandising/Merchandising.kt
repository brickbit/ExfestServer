package model.merchandising

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Merchandising(val id: Int,
                 val name: String,
                 val size: String,
                 val cost: Float,
                 val genre: String,
                 val customized: Boolean): Serializable

object Merchandisings: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name",128)
    val size = varchar("size",4)
    val cost = float("cost")
    val genre = varchar("genre",8)
    val customized = bool("customized")
}