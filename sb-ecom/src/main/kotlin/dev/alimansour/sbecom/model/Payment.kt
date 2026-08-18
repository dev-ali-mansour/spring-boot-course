package dev.alimansour.sbecom.model

import jakarta.persistence.*

@Entity
@Table(name = "payments")
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne(
        mappedBy = "payment",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE]
    )
    var order: Order? = null,

    var paymentMethod: String,

    var pgPaymentId: String,
    var pgStatus: String,
    var pgResponseMessage: String,
    var pgName: String,
)