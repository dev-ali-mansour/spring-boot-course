package com.example.autowire.annotation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Manager(@Autowired private val employee: Employee) {
//    @Autowired
//    lateinit var employee: Employee

    override fun toString(): String {
        return "Manager (employee=$employee)"
    }
}