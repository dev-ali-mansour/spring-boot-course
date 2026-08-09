package com.social.media.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity(name = "SocialGroup")
class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    var users: MutableSet<User> = mutableSetOf(),
)
