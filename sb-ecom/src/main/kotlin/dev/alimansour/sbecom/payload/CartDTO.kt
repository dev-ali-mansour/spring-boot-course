package dev.alimansour.sbecom.payload

data class CartDTO(
    val id: Long? = null,
    val totalPrice: Double = 0.0,
    val products: List<ProductDTO> = listOf(),
)