package dev.alimansour.sbecom.model

import jakarta.persistence.*

@Entity
@Table(name = "order_items")
class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn("product_id")
    var product: Product?,

    @ManyToOne
    @JoinColumn("order_id")
    var order: Order?,

    var quantity: Int,
    var discount: Double,
    var orderedProductPrice: Double,
)