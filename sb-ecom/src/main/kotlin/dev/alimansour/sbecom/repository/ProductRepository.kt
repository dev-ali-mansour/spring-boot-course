package dev.alimansour.sbecom.repository

import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByCategoryOrderByPriceAsc(category: Category): MutableList<Product>
    fun findByNameLikeIgnoreCase(name: String): MutableList<Product>
}
