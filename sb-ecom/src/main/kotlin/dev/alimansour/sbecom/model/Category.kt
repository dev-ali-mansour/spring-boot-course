package dev.alimansour.sbecom.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "categories")
class Category(
    @Id
    var id: Long = 0,
    var name: String = "",
)
