package com.project.app.exception

class MovieAlreadyExistsException(title: String) :
    RuntimeException("Movie with title '$title' already exists")
