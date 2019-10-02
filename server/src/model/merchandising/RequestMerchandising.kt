package model.merchandising

data class RequestMerchandising(
                         val name: String,
                         val size: String,
                         val cost: Float,
                         val genre: String,
                         val customized: Boolean)