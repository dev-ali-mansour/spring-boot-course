package dev.alimansour.sbecom.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class FileServiceImpl : FileService {
    override fun uploadFile(path: String, file: MultipartFile): String {
        val randomId = UUID.randomUUID().toString()
        file.originalFilename?.let { originalFilename ->
            val fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'))
            val fileName = "${randomId}${fileExtension}"
            val filePath = "$path${File.separator}$fileName"

            val folder = File(path)
            if (!folder.exists()) {
                folder.mkdir()
            }
            Files.copy(file.inputStream, Paths.get(filePath))
            return fileName
        }
        throw RuntimeException("Invalid file extension")
    }
}
