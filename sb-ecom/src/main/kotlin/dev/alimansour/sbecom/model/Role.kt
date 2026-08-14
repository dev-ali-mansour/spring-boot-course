package dev.alimansour.sbecom.model

import jakarta.persistence.*

@Entity
@Table(name = "roles")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var name: AppRole = AppRole.ROLE_USER,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val role = other as Role
        return if (id != null && role.id != null) {
            id == role.id
        } else {
            name == role.name
        }
    }

    override fun hashCode(): Int = id?.hashCode() ?: name.hashCode()

    override fun toString(): String {
        return "Role(id=$id, name='$name')"
    }
}