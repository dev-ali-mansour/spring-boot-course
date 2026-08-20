package com.project.app.exception

data class ErrorResponse(
    var status: Int = 0,
    var message: String? = null
)
