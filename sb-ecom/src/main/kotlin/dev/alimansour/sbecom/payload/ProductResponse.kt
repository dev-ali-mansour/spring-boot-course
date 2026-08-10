package dev.alimansour.sbecom.payload

data class ProductResponse(
    val content: List<ProductDTO> = listOf(),
    val pageNumber: Int = 0,
    val pageSize: Int = 0,
    val totalElements: Long = 0,
    val totalPages: Int = 0,
    val lastPage: Boolean = false,
)
