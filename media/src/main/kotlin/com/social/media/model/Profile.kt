package com.social.media.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var description: String = "",
) {
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    var user: User? = null
        set(value) {
            field = value
            if (value != null && value.profile != this) {
                value.profile = this
            }
        }
}
