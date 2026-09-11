package dev.alimansour.sbecom.repository

import dev.alimansour.sbecom.model.AppRole
import dev.alimansour.sbecom.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name=:role")
    fun findByRoleName(role: AppRole, pageable: Pageable): Page<User>
}