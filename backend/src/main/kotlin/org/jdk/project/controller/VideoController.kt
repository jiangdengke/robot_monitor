package org.jdk.project.controller

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap
import org.jdk.project.dto.video.VideoStreamDto
import org.jdk.project.dto.video.VideoStreamsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest/video")
class VideoController {
    private val activeStreams: MutableMap<String, VideoStreamDto> = ConcurrentHashMap()

    @GetMapping("/active")
    fun activeStreams(): VideoStreamsResponse = response("活跃视频流已加载")

    @PostMapping("/start")
    fun startStream(@RequestBody request: VideoStreamRequest): VideoStreamsResponse {
        val robotId = trimToDefault(request.robotId, "ROBOT-001")
        val userId = trimToDefault(request.userId, "admin")
        val now = LocalDateTime.now().format(DATETIME_FORMATTER)
        val stream =
            VideoStreamDto(
                userId = userId,
                mode = "mock",
                startTime = now,
                frameCount = 0,
                lastFrameAt = now,
            )
        activeStreams[robotId] = stream
        return response("视频流已启动")
    }

    @PostMapping("/stop")
    fun stopStream(@RequestBody request: VideoStreamRequest): VideoStreamsResponse {
        val robotId = trimToDefault(request.robotId, "")
        if (robotId.isEmpty()) {
            activeStreams.clear()
            return response("全部视频流已停止")
        }
        activeStreams.remove(robotId)
        return response("视频流已停止")
    }

    private fun response(message: String): VideoStreamsResponse =
        VideoStreamsResponse(
            data = linkedMapOf<String, VideoStreamDto>().apply { putAll(activeStreams) },
            msg = message,
        )

    private fun trimToDefault(value: String?, defaultValue: String): String {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue
        }
        return value.trim()
    }

    data class VideoStreamRequest(
        var robotId: String? = null,
        var userId: String? = null,
    )

    private companion object {
        private val DATETIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
}
