package com.example.autowire.name


class Car{
    var specification: Specification?=null

    fun displayDetails() {
        println("Car Details: $specification")
    }
}
