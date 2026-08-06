package com.ioc.coupling

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

fun main() {
    val context: ApplicationContext = ClassPathXmlApplicationContext("applicationIOCLooseCouplingExample.xml")

    val userManager = context.getBean("userManagerWithDataProvider") as UserManager
    println(userManager.getUserInfo())

    val userManagerWithWS = context.getBean("userManagerWithWebServiceProvider") as UserManager
    println(userManagerWithWS.getUserInfo())

    val userManagerWithNewDB = context.getBean("userManagerWithNewDatabaseProvider") as UserManager
    println(userManagerWithNewDB.getUserInfo())
}
