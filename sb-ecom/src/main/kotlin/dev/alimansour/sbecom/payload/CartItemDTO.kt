package dev.alimansour.sbecom.payload

data class CartItemDTO(
    val id: Long? = null,
    val productDTO: ProductDTO,
    val quantity: Int,
    val discount: Double,
    val price: Double,
)
