package com.lose.coupling

fun main() {
    val databaseProvider: UserDatabaseProvider = UserDatabaseProvider()
    val userManager = UserManager(userDataProvider=databaseProvider)
    println(userManager.getUserInfo())

    val webServiceProvider: UserDataProvider = WebServiceDataProvider()
    val userManagerWithWS= UserManager(userDataProvider = webServiceProvider)
    println(userManagerWithWS.getUserInfo())

    val newDatabaseProvider: UserDataProvider = NewDatabaseProvider()
    val userManagerWithNewDB= UserManager(userDataProvider = newDatabaseProvider)
    println(userManagerWithNewDB.getUserInfo())

}
