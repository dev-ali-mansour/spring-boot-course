package com.project.app.exception

class MovieNotFoundException(id: Long) : RuntimeException("Movie not found with id: $id")
