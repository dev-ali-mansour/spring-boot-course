package com.example.autowire.type

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

fun main() {
    val context: ApplicationContext = ClassPathXmlApplicationContext("autowireByType.xml")

    val myCar: Car = context.getBean("myCar") as Car
    myCar.displayDetails()
}
