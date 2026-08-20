package com.project.app.repository

import com.project.app.model.Employee
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
    fun findByDepartment(department: String, pageable: Pageable): Page<Employee>
}
