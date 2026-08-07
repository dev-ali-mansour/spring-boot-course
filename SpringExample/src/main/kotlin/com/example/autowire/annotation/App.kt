package com.example.autowire.annotation

import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {
    val context: ApplicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)

    val employee: Employee = context.getBean<Employee>("employee")
    val manager = context.getBean<Manager>("manager")

    println(employee)
    println(manager)
}