package com.example.autowire.annotation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class Manager(@Autowired @Qualifier("employee") private val employee: Employee) {
//    @Autowired
//    @Qualifier("employee")
//    lateinit var employee: Employee

    override fun toString(): String {
        return "Manager (employee=$employee)"
    }
}