package car.example.bean

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

fun main() {
    val context: ApplicationContext = ClassPathXmlApplicationContext("applicationBeanContext.xml")

    val myBean = context.getBean("myBean")
    println(myBean)

}