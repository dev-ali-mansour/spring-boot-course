package com.example.autowire.name

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

fun main() {
    val context: ApplicationContext = ClassPathXmlApplicationContext("autowireByName.xml")

    val myCar: Car = context.getBean("myCar") as Car
    myCar.displayDetails()
}
