package model.transport


import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Transport(val id: Int,
                     val name: String,
                     val number: Int,
                     val cost: Float,
                     val shared: Boolean,
                     val dateRequest: String,
                     val dateArrive: String,
                     val dateExit: String): Serializable

object Transports: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name",255)
    val number = integer("number")
    val cost = float("cost")
    val shared = bool("shared")
    val dateRequest = varchar("date_request",32)
    val dateArrive = varchar("date_arrive",32)
    val dateExit = varchar("date_exit",32)
}