package org.jdk.project.controller.config

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.AudioDto
import org.jdk.project.dto.config.AudioUpsertRequest
import org.jdk.project.dto.config.ImageDto
import org.jdk.project.dto.config.ImageUpsertRequest
import org.jdk.project.service.ConfigCommandService
import org.jdk.project.service.ConfigQueryService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/config")
class MediaConfigController(
    private val configCommandService: ConfigCommandService,
    private val configQueryService: ConfigQueryService,
) {
    @GetMapping("/images")
    fun listImages(): ListResponse<ImageDto> = configQueryService.listImages()

    @PostMapping("/images")
    fun createImage(@RequestBody request: ImageUpsertRequest): Long? = configCommandService.createImage(request)

    @PutMapping("/images/{id}")
    fun updateImage(@PathVariable id: Long, @RequestBody request: ImageUpsertRequest) {
        configCommandService.updateImage(id, request)
    }

    @DeleteMapping("/images/{id}")
    fun deleteImage(@PathVariable id: Long) {
        configCommandService.deleteImage(id)
    }

    @GetMapping("/audios")
    fun listAudios(@RequestParam(required = false) category: String?): ListResponse<AudioDto> =
        configQueryService.listAudios(category)

    @PostMapping("/audios")
    fun createAudio(@RequestBody request: AudioUpsertRequest): Long? = configCommandService.createAudio(request)

    @PutMapping("/audios/{id}")
    fun updateAudio(@PathVariable id: Long, @RequestBody request: AudioUpsertRequest) {
        configCommandService.updateAudio(id, request)
    }

    @DeleteMapping("/audios/{id}")
    fun deleteAudio(@PathVariable id: Long) {
        configCommandService.deleteAudio(id)
    }
}
