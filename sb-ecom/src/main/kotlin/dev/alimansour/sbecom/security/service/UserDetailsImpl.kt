package dev.alimansour.sbecom.security.service

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.alimansour.sbecom.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    val id: Long? = null,
    private val username: String = "",
    val email: String = "",
    @JsonIgnore
    private val password: String = "",
    private val authorities: Collection<GrantedAuthority> = emptyList()
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
    override fun getPassword(): String = password
    override fun getUsername(): String = username
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as UserDetailsImpl
        return if (id != null && user.id != null) {
            id == user.id
        } else {
            username == user.username
        }
    }

    override fun hashCode(): Int = id?.hashCode() ?: username.hashCode()

    override fun toString(): String {
        return "UserDetailsImpl(id=$id, username='$username', email='$email', authorities=$authorities)"
    }

    companion object {
        private const val serialVersionUID = 1L

        fun build(user: User): UserDetailsImpl {
            val authorities: List<GrantedAuthority> = user.roles
                .map { SimpleGrantedAuthority(it.name.name) }

            return UserDetailsImpl(
                id = user.id,
                username = user.username,
                email = user.email,
                password = user.password,
                authorities = authorities
            )
        }
    }
}