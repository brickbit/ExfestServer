package model.transport

data class RequestTransport(
                     val kind: String,
                     val cost: Float,
                     val shared: Boolean,
                     val dateRequest: String,
                     val dateArrive: String,
                     val dateExit: String)