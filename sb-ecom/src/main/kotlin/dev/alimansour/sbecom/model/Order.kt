package dev.alimansour.sbecom.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var email: String,

    @OneToMany(
        mappedBy = "order",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        orphanRemoval = true
    )
    var orderItems: MutableList<OrderItem> = mutableListOf(),

    var orderDate: LocalDate,

    @OneToOne
    @JoinColumn(name = "payment_id")
    var payment: Payment,

    var totalAmount: Double,
    var orderStatus: String,

    @ManyToOne
    @JoinColumn(name = "address_id")
    var address: Address?
)