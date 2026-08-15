package dev.alimansour.sbecom.model

import jakarta.persistence.*

@Entity
@Table(name = "cart_items")
class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    var cart: Cart? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product: Product? = null,

    var quantity: Int,

    var discount: Double,

    var price: Double,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val cartItem = other as CartItem
        return id != null && id == cartItem.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "CartItem(id=$id, quantity=$quantity, discount=$discount, price=$price)"
    }
}