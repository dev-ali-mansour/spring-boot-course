package com.lose.coupling

// A - MYSQL, POSTGRESQL
// B - Web Service, MongoDB

class UserDatabaseProvider : UserDataProvider {
    override fun getUserDetails(): String {
        // Directly access database here
        return "User Details From Database"
    }
}
