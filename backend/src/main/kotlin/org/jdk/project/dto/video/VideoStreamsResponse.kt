package org.jdk.project.dto.video

data class VideoStreamsResponse(
    var data: Map<String, VideoStreamDto>? = null,
    var msg: String? = null,
)
