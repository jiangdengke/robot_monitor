package org.jdk.project.dto.video

data class VideoStreamDto(
    var userId: String? = null,
    var mode: String? = null,
    var startTime: String? = null,
    var frameCount: Int? = null,
    var lastFrameAt: String? = null,
)
