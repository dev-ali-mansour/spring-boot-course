package car.example.constructor.injection

class Car(private val specification: Specification) {
    fun displayDetails(){
        println("Car Details: $specification")
    }
}
