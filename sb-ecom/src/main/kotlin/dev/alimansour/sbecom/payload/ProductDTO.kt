package dev.alimansour.sbecom.payload

data class ProductDTO(
    val id: Long? = null,
    val name: String = "",
    val image: String = "",
    val quantity: Int = 0,
    val price: Double = 0.0,
    val discount: Double = 0.0,
    val specialPrice: Double = 0.0,
)
