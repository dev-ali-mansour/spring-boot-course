package dev.alimansour.sbecom.payload

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Range

data class ProductDTO(
    val id: Long? = null,
    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 3, message = "Product name must be at least 3 characters")
    val name: String,
    val image: String = "",
    @NotBlank(message = "Product description cannot be blank")
    @Size(min = 6, message = "Product description must be at least 6 characters")
    val description: String,
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    val quantity: Int,
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    val price: Double,
    @Range(min = 0, max = 100, message = "Discount must be between 0 and 100")
    val discount: Double,
    val specialPrice: Double = 0.0,
)
