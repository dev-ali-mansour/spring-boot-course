package dev.alimansour.sbecom.payload

data class OrderItemDTO(
    val id: Long? = null,
    val product: ProductDTO,
    val quantity: Int,
    val discount: Double,
    val orderedProductPrice: Double,
)
