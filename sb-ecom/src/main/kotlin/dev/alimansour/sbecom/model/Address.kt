package dev.alimansour.sbecom.model

import jakarta.persistence.*

@Entity
@Table(name = "addresses")
class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var street: String = "",
    var buildingName: String = "",
    var city: String = "",
    var state: String = "",
    var country: String = "",
    var pinCode: String = "",

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val address = other as Address
        return if (id != null && address.id != null) {
            id == address.id
        } else {
            street == address.street &&
                    buildingName == address.buildingName &&
                    city == address.city &&
                    pinCode == address.pinCode
        }
    }

    override fun hashCode(): Int = id?.hashCode() ?: (street + buildingName + pinCode).hashCode()

    override fun toString(): String {
        return "Address(id=$id, street='$street', buildingName='$buildingName', city='$city', state='$state', country='$country', pinCode='$pinCode')"
    }
}