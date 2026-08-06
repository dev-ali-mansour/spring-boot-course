package com.lose.coupling

class UserManager(private val userDataProvider: UserDataProvider) {
    fun getUserInfo(): String {
        return userDataProvider.getUserDetails()
    }
}
