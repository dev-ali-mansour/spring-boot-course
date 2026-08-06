package com.example.autowire.type


class Car{
    var specification: Specification?=null

    fun displayDetails() {
        println("Car Details: $specification")
    }
}
