package dev.alimansour.sbecom.payload

data class OrderResponse(
    val content: List<OrderDTO> = listOf(),
    val pageNumber: Int = 0,
    val pageSize: Int = 0,
    val totalElements: Long = 0,
    val totalPages: Int = 0,
    val lastPage: Boolean = false,
)
