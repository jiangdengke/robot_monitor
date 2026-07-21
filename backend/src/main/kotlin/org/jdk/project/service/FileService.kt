package org.jdk.project.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.time.LocalDate
import java.util.UUID

@Service
class FileService(
    @Value("\${app.upload-dir:/tmp/robot-monitor/uploads}")
    private val uploadDir: String,
) {
    fun saveFiles(files: List<MultipartFile>): List<String> {
        val targetDirectory = Path.of(uploadDir, LocalDate.now().toString())
        Files.createDirectories(targetDirectory)
        return files.map { file -> saveFile(targetDirectory, file) }
    }

    private fun saveFile(
        targetDirectory: Path,
        file: MultipartFile,
    ): String {
        try {
            val originalName = file.originalFilename ?: "file.bin"
            val targetName = "${UUID.randomUUID()}-$originalName"
            val targetFile = targetDirectory.resolve(targetName)
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING)
            }
            return targetFile.toString()
        } catch (exception: Exception) {
            throw RuntimeException("保存文件失败", exception)
        }
    }
}
