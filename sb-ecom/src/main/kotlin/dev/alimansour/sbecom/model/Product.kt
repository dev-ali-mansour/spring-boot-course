package dev.alimansour.sbecom.model

import jakarta.persistence.*

@Entity
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String,
    var image: String,
    var description: String,
    var quantity: Int,
    var price: Double,
    var discount: Double,
    var specialPrice: Double,
) {

    @ManyToOne
    @JoinColumn(name = "category_id")
    lateinit var category: Category


}