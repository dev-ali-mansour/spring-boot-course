package dev.alimansour.sbecom.service

import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun uploadFile(path: String, file: MultipartFile): String
}
