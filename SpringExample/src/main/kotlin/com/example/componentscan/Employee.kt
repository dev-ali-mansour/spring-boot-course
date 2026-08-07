package com.example.componentscan

import org.springframework.stereotype.Component

@Component("employee")
class Employee {
    var employeeId: Int = 0
    var firstName: String? = null
    var lastName: String? = null
    var salary: Double = 0.0

    override fun toString(): String {
        return "Employee(employeeId=$employeeId, firstName='$firstName', lastName='$lastName', salary=$salary)"
    }
}
