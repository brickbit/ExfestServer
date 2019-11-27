package model.video

data class RequestVideo(
                 val title: String,
                 val size: Float,
                 val visits: Int,
                 val topic: Int,
                 val url: String)