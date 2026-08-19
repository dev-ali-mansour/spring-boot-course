package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.config.AppConstants
import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.payload.ProductDTO
import dev.alimansour.sbecom.payload.ProductResponse
import dev.alimansour.sbecom.service.ProductService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api")
class ProductController(private val productService: ProductService) {

    @Tag(name = "Product APIs", description = "APIs for managing products")
    @PostMapping("/admin/categories/{categoryId}/product")
    fun addProduct(
        @PathVariable categoryId: Long,
        @Validated @RequestBody product: ProductDTO,
    ): ResponseEntity<ProductDTO> {
        val productDTO = productService.addProduct(categoryId, product)
        return ResponseEntity(productDTO, HttpStatus.CREATED)
    }

    @Tag(name = "Product APIs", description = "APIs for managing products")
    @GetMapping("/public/products")
    fun getAllProducts(
        @PageableDefault(
            page = AppConstants.PAGE_NUMBER,
            size = AppConstants.PAGE_SIZE,
            sort = [AppConstants.SORT_BY],
            direction = Sort.Direction.ASC
        ) pageable: Pageable,
    ): ResponseEntity<ProductResponse> =
        ResponseEntity(productService.getAllProducts(pageable), HttpStatus.OK)

    @Tag(name = "Product APIs", description = "APIs for managing products")
    @GetMapping("/public/categories/{categoryId}/products")
    fun getProductsByCategoryId(
        @PathVariable categoryId: Long,
        @PageableDefault(
            page = AppConstants.PAGE_NUMBER,
            size = AppConstants.PAGE_SIZE,
            sort = ["price"],
            direction = Sort.Direction.ASC
        ) pageable: Pageable,
    ): ResponseEntity<ProductResponse> =
        ResponseEntity(productService.searchByCategory(categoryId, pageable), HttpStatus.OK)

    @Tag(name = "Product APIs", description = "APIs for managing products")
    @GetMapping("/public/products/keyword/{keyword}")
    fun getProductsByKeyword(
        @PathVariable keyword: String,
        @PageableDefault(
            page = AppConstants.PAGE_NUMBER,
            size = AppConstants.PAGE_SIZE,
            sort = [AppConstants.SORT_BY],
            direction = Sort.Direction.ASC
        ) pageable: Pageable,
    ): ResponseEntity<ProductResponse> =
        ResponseEntity(productService.searchByKeyword(keyword, pageable), HttpStatus.OK)

    @Tag(name = "Product APIs", description = "APIs for managing products")
    @PutMapping("/admin/products/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @Validated @RequestBody productDTO: ProductDTO
    ): ResponseEntity<ProductDTO> {
        if (productDTO.id != null && productDTO.id != id) {
            throw APIException(message = "Resource ID mismatch: URL path specifies id $id, but the request body contains ${productDTO.id}")
        }
        return ResponseEntity(productService.updateProduct(id, productDTO), HttpStatus.OK)
    }

    @Tag(name = "Product APIs", description = "APIs for managing products")
    @DeleteMapping("/admin/products/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<ProductDTO> =
        ResponseEntity(productService.deleteProduct(id), HttpStatus.OK)

    @Tag(name = "Product APIs", description = "APIs for managing products")
    @PutMapping("/admin/products/{id}/image")
    fun updateProductImage(
        @PathVariable id: Long,
        @RequestParam("image") image: MultipartFile,
    ): ResponseEntity<ProductDTO> =
        ResponseEntity(productService.updateProductImage(id, image), HttpStatus.OK)
}
