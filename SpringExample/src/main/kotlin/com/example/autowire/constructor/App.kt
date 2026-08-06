package com.example.autowire.constructor

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

fun main() {
    val context: ApplicationContext = ClassPathXmlApplicationContext("autowireByConstructor.xml")

    val myCar: Car = context.getBean("myCar") as Car
    myCar.displayDetails()
}
