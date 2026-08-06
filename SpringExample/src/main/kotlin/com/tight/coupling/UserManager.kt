package com.tight.coupling

import com.lose.coupling.UserDatabaseProvider

class UserManager {
    private val userDatabaseProvider = UserDatabaseProvider()
    fun getUserInfo(): String {
        return userDatabaseProvider.getUserDetails()
    }
}
