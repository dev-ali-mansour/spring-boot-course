package dev.alimansour.sbecom.model

import jakarta.persistence.*

@Entity
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String = "",
    var image: String = "default.png",
    var description: String = "",
    var quantity: Int = 0,
    var price: Double = 0.0,
    var discount: Double = 0.0,
    var specialPrice: Double = 0.0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: Category? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    var user: User? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val product = other as Product
        return if (id != null && product.id != null) {
            id == product.id
        } else {
            name.equals(product.name, ignoreCase = true)
        }
    }

    override fun hashCode(): Int = id?.hashCode() ?: name.lowercase().hashCode()

    override fun toString(): String {
        return "Product(id=$id, name='$name', image='$image', description='$description', quantity=$quantity, price=$price, discount=$discount, specialPrice=$specialPrice)"
    }
}