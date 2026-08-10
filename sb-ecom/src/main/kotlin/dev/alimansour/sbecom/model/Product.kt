package dev.alimansour.sbecom.model

import jakarta.persistence.*

@Entity
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String = "",
    var image: String = "",
    var description: String = "",
    var quantity: Int = 0,
    var price: Double = 0.0,
    var discount: Double = 0.0,
    var specialPrice: Double = 0.0,
) {

    @ManyToOne
    @JoinColumn(name = "category_id")
    lateinit var category: Category


}