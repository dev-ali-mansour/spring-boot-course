package dev.alimansour.sbecom.model

import jakarta.persistence.*

@Entity(name = "categories")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String,
) {
    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL])
    lateinit var products: MutableList<Product>
}
