package dev.alimansour.sbecom.model

import jakarta.persistence.*

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["username"]),
        UniqueConstraint(columnNames = ["email"])
    ]
)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var firstName: String = "",
    @Column(nullable = false)
    var lastName: String = "",
    @Column(nullable = false)
    var username: String = "",
    @Column(nullable = false)
    var email: String = "",
    @Column(nullable = false)
    var password: String = "",

    @ManyToMany(
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf(),

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        orphanRemoval = true
    )
    var addresses: MutableList<Address> = mutableListOf(),

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        orphanRemoval = true
    )
    var products: MutableSet<Product> = mutableSetOf(),

    @OneToOne(
        mappedBy = "user",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        orphanRemoval = true
    )
    var cart: Cart? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as User
        return if (id != null && user.id != null) {
            id == user.id
        } else {
            username == user.username
        }
    }

    override fun hashCode(): Int = id?.hashCode() ?: username.hashCode()

    override fun toString(): String {
        return "User(id=$id, username='$username', email='$email')"
    }
}