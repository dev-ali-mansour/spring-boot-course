package com.example.autowire.annotation

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component("employee")
class Employee {
    var employeeId: Int = 0
    @Value("Hello")
    var firstName: String? = null
    @Value($$"${java.home}")
    var lastName: String? = null
    @Value("#{4*4}")
    var salary: Double = 0.0

    override fun toString(): String {
        return "Employee(employeeId=$employeeId, firstName='$firstName', lastName='$lastName', salary=$salary)"
    }
}
