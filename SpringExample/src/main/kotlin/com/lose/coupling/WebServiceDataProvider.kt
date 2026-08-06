package com.lose.coupling

class WebServiceDataProvider : UserDataProvider {
    override fun getUserDetails(): String {
        return "Fetching Data From Web Service"
    }
}
