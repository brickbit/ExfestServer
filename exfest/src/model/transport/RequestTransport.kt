package model.transport

data class RequestTransport(
                     val name:String,
                     val number: Int,
                     val cost: Float,
                     val shared: Boolean,
                     val dateRequest: String,
                     val dateArrive: String,
                     val dateExit: String)