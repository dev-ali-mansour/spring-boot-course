package dev.alimansour.sbecom.mapper

import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.payload.CategoryDTO

fun Category.toDTO(): CategoryDTO = CategoryDTO(
    id = this.id,
    name = this.name,
)

fun CategoryDTO.toEntity(): Category = Category(
    id = this.id,
    name = this.name,
)