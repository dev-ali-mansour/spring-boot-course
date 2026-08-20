package com.project.app.exception

class DuplicateRatingException(reviewer: String, movieId: Long) :
    RuntimeException("Reviewer '$reviewer' has already rated movie with id: $movieId")
