package dev.alimansour.sbecom.payload

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CategoryDTO(
    val id: Long? = null,
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, message = "Category name must be at least 5 characters")
    val name: String,
)
