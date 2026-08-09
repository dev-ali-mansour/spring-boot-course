package com.social.media.controller

import com.social.media.model.User
import com.social.media.service.SocialService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SocialController(private val socialService: SocialService) {

    @GetMapping("/social/users")
    fun getUsers(): ResponseEntity<List<User>> =
        ResponseEntity(socialService.getAllUsers(), HttpStatus.OK)

    @PostMapping("/social/users")
    fun saveUser(@Validated @RequestBody user: User): ResponseEntity<User> =
        ResponseEntity(socialService.saveUser(user), HttpStatus.OK)

}
