package dev.alimansour.sbecom.payload

data class ProductDTO(
    val id: Long? = null,
    val name: String,
    val image: String = "",
    val description: String,
    val quantity: Int,
    val price: Double,
    val discount: Double,
    val specialPrice: Double = 0.0,
)
