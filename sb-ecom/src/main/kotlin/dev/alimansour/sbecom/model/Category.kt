package dev.alimansour.sbecom.model

import jakarta.persistence.*

@Entity
@Table(name = "categories")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String = "",

    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], orphanRemoval = true)
    var products: MutableList<Product> = mutableListOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val category = other as Category
        return if (id != null && category.id != null) {
            id == category.id
        } else {
            name.equals(category.name, ignoreCase = true)
        }
    }

    override fun hashCode(): Int = id?.hashCode() ?: name.lowercase().hashCode()

    override fun toString(): String {
        return "Category(id=$id, name='$name')"
    }
}
