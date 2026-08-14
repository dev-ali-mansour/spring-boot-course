package dev.alimansour.sbecom.repository

import dev.alimansour.sbecom.model.AppRole
import dev.alimansour.sbecom.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(appRole: AppRole): Optional<Role>
}