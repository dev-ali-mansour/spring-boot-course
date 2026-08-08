package com.bank.app.model

import jakarta.persistence.*

@Entity(name = "customers")
class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    @Column(unique = true)
    var email: String = "",
    var phoneNumber: String = "",
)