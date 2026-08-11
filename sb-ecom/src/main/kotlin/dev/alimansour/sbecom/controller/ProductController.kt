package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.model.Product
import dev.alimansour.sbecom.payload.ProductDTO
import dev.alimansour.sbecom.payload.ProductResponse
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

    @GetMapping("/public/products")
    fun getAllProducts(): ResponseEntity<ProductResponse> =
        ResponseEntity(productService.getAllProducts(), HttpStatus.OK)

    @GetMapping("/public/categories/{categoryId}/products")
    fun getProductsByCategoryId(@PathVariable categoryId: Long): ResponseEntity<ProductResponse> =
        ResponseEntity(productService.searchByCategory(categoryId), HttpStatus.OK)

    @GetMapping("/public/products/keyword/{keyword}")
    fun getProductsByKeyword(@PathVariable keyword: String): ResponseEntity<ProductResponse> =
        ResponseEntity(productService.searchByKeyword(keyword), HttpStatus.OK)
}
