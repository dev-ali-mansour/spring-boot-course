package dev.alimansour.sbecom.model

import jakarta.persistence.*

@Entity
@Table(name = "carts")
class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @OneToMany(
        mappedBy = "cart",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE],
        orphanRemoval = true,
    )
    var cartItems: MutableList<CartItem> = mutableListOf(),

    var totalPrice: Double = 0.0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val cart = other as Cart
        return id != null && id == cart.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "Cart(id=$id, totalPrice=$totalPrice)"
    }
}