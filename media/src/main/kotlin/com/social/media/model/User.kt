package com.social.media.model

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import java.util.*

@Entity(name = "SocialUser")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    @OneToOne(mappedBy = "user", cascade = [(CascadeType.ALL)])
    var profile: Profile? = null
        set(value) {
            field = value
            if (value != null && value.user != this) {
                value.user = this
            }
        }

    @OneToMany(mappedBy = "user")
    var posts: MutableList<Post> = mutableListOf()

    @ManyToMany
    @JoinTable(
        name = "user_group",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    var groups: MutableSet<Group> = mutableSetOf()

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as User

        return id != null && id == other.id
    }
}
