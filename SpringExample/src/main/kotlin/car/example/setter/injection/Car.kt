package car.example.setter.injection

class Car {
    var specification: Specification? = null
    fun displayDetails() {
        println("Car Details: $specification")
    }
}
