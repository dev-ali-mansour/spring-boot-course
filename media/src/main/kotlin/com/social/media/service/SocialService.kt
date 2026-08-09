package com.social.media.service

import com.social.media.model.User

interface SocialService {
    fun getAllUsers(): List<User>
    fun saveUser(user: User): User

}
