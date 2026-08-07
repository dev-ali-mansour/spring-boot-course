package com.example.componentscan.annotations

import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {
    val context: ApplicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)

    val employee: Employee = context.getBean<Employee>("employee")
    println(employee)
}