package dev.alimansour.sbecom

import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.payload.CategoryDTO

fun Category.toCategoryDTO(): CategoryDTO = CategoryDTO(
    id = this.id,
    name = this.name,
)

fun CategoryDTO.toCategory(): Category = Category(
    id = this.id,
    name = this.name,
)