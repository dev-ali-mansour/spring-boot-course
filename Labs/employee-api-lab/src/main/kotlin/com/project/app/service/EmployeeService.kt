package com.project.app.service

import com.project.app.payload.EmployeeResponse
import com.project.app.repository.EmployeeRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository
) {
    fun getAllEmployees(pageable: Pageable): EmployeeResponse {
        val page = employeeRepository.findAll(pageable)
        return EmployeeResponse(
            content = page.content,
            pageNumber = page.number,
            pageSize = page.size,
            totalElements = page.totalElements,
            totalPages = page.totalPages,
            lastPage = page.isLast
        )
    }

    fun getEmployeesByDepartment(department: String, pageable: Pageable): EmployeeResponse {
        val page = employeeRepository.findByDepartment(department, pageable)
        return EmployeeResponse(
            content = page.content,
            pageNumber = page.number,
            pageSize = page.size,
            totalElements = page.totalElements,
            totalPages = page.totalPages,
            lastPage = page.isLast
        )
    }
}
