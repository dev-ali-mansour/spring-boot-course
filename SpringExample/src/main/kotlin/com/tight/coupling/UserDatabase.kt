package com.tight.coupling

// A - MYSQL, POSTGRESQL
// B - Web Service, MongoDB

class UserDatabase {
    fun getUserDetails(): String {
        // Directly access database here
        return "User Details From Database"
    }
}

