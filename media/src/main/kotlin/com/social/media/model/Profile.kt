package com.social.media.model

import jakarta.persistence.*

@Entity
class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null,

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,
)
