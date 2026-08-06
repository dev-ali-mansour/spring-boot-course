package com.ioc.coupling

class NewDatabaseProvider : UserDataProvider {
    override fun getUserDetails(): String {
        return "New Database in action"
    }
}
