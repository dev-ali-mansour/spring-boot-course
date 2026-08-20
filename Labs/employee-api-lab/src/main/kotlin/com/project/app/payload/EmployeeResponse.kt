package com.project.app.payload

import com.project.app.model.Employee

data class EmployeeResponse(
    val content: List<Employee> = listOf(),
    val pageNumber: Int = 0,
    val pageSize: Int = 0,
    val totalElements: Long = 0,
    val totalPages: Int = 0,
    val lastPage: Boolean = false,
)
