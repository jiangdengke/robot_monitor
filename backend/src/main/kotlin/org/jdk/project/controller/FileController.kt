package org.jdk.project.controller

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.time.LocalDate
import java.util.UUID
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/files")
class FileController(
    @Value("\${app.upload-dir:/tmp/robot-monitor/uploads}")
    private val uploadDir: String,
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestPart("files") files: List<MultipartFile>): List<String> {
        val root = Path.of(uploadDir, LocalDate.now().toString())
        Files.createDirectories(root)
        return files.map { file -> saveFile(root, file) }
    }

    private fun saveFile(root: Path, file: MultipartFile): String {
        try {
            val originalName = file.originalFilename ?: "file.bin"
            val targetName = UUID.randomUUID().toString() + "-" + originalName
            val target = root.resolve(targetName)
            file.inputStream.use { input -> Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING) }
            return target.toString()
        } catch (exception: Exception) {
            throw RuntimeException("保存文件失败", exception)
        }
    }
}
