package dev.alimansour.sbecom.security.service

import dev.alimansour.sbecom.model.AppRole
import dev.alimansour.sbecom.model.Role
import dev.alimansour.sbecom.model.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.security.core.authority.SimpleGrantedAuthority

class UserDetailsImplTest {

    @Test
    fun `build should create UserDetailsImpl from User with mapped authorities`() {
        val roleUser = Role(id = 1, name = AppRole.ROLE_USER)
        val roleAdmin = Role(id = 2, name = AppRole.ROLE_ADMIN)
        val user = User(
            id = 100L,
            username = "testuser",
            email = "test@example.com",
            password = "secretpassword"
        ).apply {
            roles = mutableSetOf(roleUser, roleAdmin)
        }

        val userDetails = UserDetailsImpl.build(user)

        assertEquals(100L, userDetails.id)
        assertEquals("testuser", userDetails.username)
        assertEquals("test@example.com", userDetails.email)
        assertEquals("secretpassword", userDetails.password)
        assertTrue(userDetails.isAccountNonExpired)
        assertTrue(userDetails.isAccountNonLocked)
        assertTrue(userDetails.isCredentialsNonExpired)
        assertTrue(userDetails.isEnabled)

        val authorities = userDetails.authorities.map { it.authority }
        assertEquals(2, authorities.size)
        assertTrue(authorities.contains("ROLE_USER"))
        assertTrue(authorities.contains("ROLE_ADMIN"))
    }

    @Test
    fun `equals and hashCode contract should work with matching IDs`() {
        val user1 = UserDetailsImpl(
            id = 1L,
            username = "user1",
            email = "user1@example.com",
            password = "pass",
            authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
        )
        val user2 = UserDetailsImpl(
            id = 1L,
            username = "user1_diff_name",
            email = "user1_diff@example.com",
            password = "pass",
            authorities = emptyList()
        )
        val user3 = UserDetailsImpl(
            id = 2L,
            username = "user1",
            email = "user1@example.com",
            password = "pass",
            authorities = emptyList()
        )

        assertEquals(user1, user2)
        assertEquals(user1.hashCode(), user2.hashCode())
        assertNotEquals(user1, user3)
    }

    @Test
    fun `equals and hashCode contract should fallback to username when ID is null`() {
        val user1 = UserDetailsImpl(
            id = null,
            username = "sameuser",
            email = "user1@example.com",
            password = "pass",
            authorities = emptyList()
        )
        val user2 = UserDetailsImpl(
            id = null,
            username = "sameuser",
            email = "user2@example.com",
            password = "pass",
            authorities = emptyList()
        )
        val user3 = UserDetailsImpl(
            id = null,
            username = "otheruser",
            email = "user1@example.com",
            password = "pass",
            authorities = emptyList()
        )

        assertEquals(user1, user2)
        assertEquals(user1.hashCode(), user2.hashCode())
        assertNotEquals(user1, user3)
    }

    @Test
    fun `toString should include UserDetailsImpl prefix and non-sensitive fields`() {
        val userDetails = UserDetailsImpl(
            id = 42L,
            username = "johndoe",
            email = "john@example.com",
            password = "supersecretpassword",
            authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
        )

        val str = userDetails.toString()

        assertTrue(str.startsWith("UserDetailsImpl("))
        assertTrue(str.contains("id=42"))
        assertTrue(str.contains("username='johndoe'"))
        assertTrue(str.contains("email='john@example.com'"))
        assertFalse(str.contains("supersecretpassword"))
    }
}
