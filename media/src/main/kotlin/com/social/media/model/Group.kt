package com.social.media.model

import jakarta.persistence.*

@Entity
class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToMany(mappedBy = "groups")
    var users: MutableSet<User> = mutableSetOf(),
)
