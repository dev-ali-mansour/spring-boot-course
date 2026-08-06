package com.example.autowire.constructor


class Car(private val specification: Specification){

    fun displayDetails() {
        println("Car Details: $specification")
    }
}
