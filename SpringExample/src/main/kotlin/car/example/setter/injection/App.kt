package car.example.setter.injection

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

fun main() {
    val context: ApplicationContext = ClassPathXmlApplicationContext("applicationSetterInjection.xml")

    val myCar: Car = context.getBean("myCar") as Car
    myCar.displayDetails()
}
