package com.ioc.coupling

class UserManager(private val userDataProvider: UserDataProvider) {
    fun getUserInfo(): String {
        return userDataProvider.getUserDetails()
    }
}
