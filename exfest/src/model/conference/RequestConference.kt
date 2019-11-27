package model.conference

data class RequestConference(val title:String,
                             val hour: String,
                             val duration: Float,
                             val speaker: Int,
                             val topic: Int,
                             val description: String,
                             val streaming: Boolean,
                             val video: Int
                             )

