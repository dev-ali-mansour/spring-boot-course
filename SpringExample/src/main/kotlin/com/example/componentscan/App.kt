package com.example.componentscan

import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

fun main() {
    val context: ApplicationContext = ClassPathXmlApplicationContext("componentScanDemo.xml")

    val employee: Employee = context.getBean<Employee>("employee")
    println(employee)
}