package dev.alimansour.sbecom.mapper

import dev.alimansour.sbecom.model.Product
import dev.alimansour.sbecom.payload.ProductDTO

fun Product.toDTO(): ProductDTO = ProductDTO(
    id = this.id,
    name = this.name,
    image = this.image,
    quantity = this.quantity,
    price = this.price,
    discount = this.discount,
    specialPrice = this.specialPrice,
)

fun ProductDTO.toEntity(): Product = Product(
    id = this.id,
    name = this.name,
    image = this.image,
    quantity = this.quantity,
    price = this.price,
    discount = this.discount,
    specialPrice = this.specialPrice,
)