package dev.alimansour.sbecom.payload

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CategoryDTO(
    @Schema(description = "Category ID", example = "101")
    val id: Long? = null,
    @Schema(description = "Category Name for the category you wish to create", example = "Mobiles")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, message = "Category name must be at least 5 characters")
    val name: String,
)
