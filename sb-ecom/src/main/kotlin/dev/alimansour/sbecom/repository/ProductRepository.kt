package dev.alimansour.sbecom.repository

import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.model.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByCategory(category: Category, pageable: Pageable): Page<Product>
    fun findByNameLikeIgnoreCase(name: String, pageable: Pageable): Page<Product>
}
