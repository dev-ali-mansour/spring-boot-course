package car.example.constructor.injection

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

fun main() {
    val context: ApplicationContext = ClassPathXmlApplicationContext("applicationConstructorInjection.xml")

    val myCar: Car = context.getBean("myCar") as Car
    myCar.displayDetails()
}
