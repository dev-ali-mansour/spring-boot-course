package com.project.app.controller

import com.project.app.payload.EmployeeResponse
import com.project.app.service.EmployeeService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/employees")
class EmployeeController(
    private val employeeService: EmployeeService
) {
    @GetMapping
    fun getEmployees(
        @PageableDefault(size = 10, sort = ["lastName"]) pageable: Pageable,
        @RequestParam(required = false) department: String?
    ): ResponseEntity<EmployeeResponse> {
        if (!department.isNullOrBlank()) {
            return ResponseEntity.ok(employeeService.getEmployeesByDepartment(department, pageable))
        }
        return ResponseEntity.ok(employeeService.getAllEmployees(pageable))
    }
}
