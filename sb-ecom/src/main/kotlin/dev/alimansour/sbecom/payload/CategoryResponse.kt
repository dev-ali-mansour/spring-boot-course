package dev.alimansour.sbecom.payload

data class CategoryResponse(
    val content: List<CategoryDTO>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int,
    val lastPage: Boolean,
)
