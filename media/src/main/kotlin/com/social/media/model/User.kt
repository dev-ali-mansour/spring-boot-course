package com.social.media.model

import jakarta.persistence.*

@Entity(name = "SocialUser")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null,

    @OneToOne(mappedBy = "user")
    var profile: Profile? = null,

    @OneToMany(mappedBy = "user")
    var posts: MutableList<Post> = mutableListOf(),

    @ManyToMany
    @JoinTable(
        name = "user_group",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    var groups: MutableSet<Group> = mutableSetOf()
)
