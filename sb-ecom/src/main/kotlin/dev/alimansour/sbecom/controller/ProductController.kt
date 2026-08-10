package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.model.Product
import dev.alimansour.sbecom.payload.ProductDTO
import dev.alimansour.sbecom.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ProductController(private val productService: ProductService) {

    @PostMapping("/admin/categories/{categoryId}/product")
    fun addProduct(
        @Validated @RequestBody product: Product,
        @PathVariable categoryId: Long
    ): ResponseEntity<ProductDTO> {
        val productDTO = productService.addProduct(categoryId, product)
        return ResponseEntity(productDTO, HttpStatus.CREATED)
    }
}
