package com.social.media.service

import com.social.media.model.User
import com.social.media.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class SocialServiceImpl(
    private val userRepository: UserRepository,
) : SocialService {
    override fun getAllUsers(): List<User> =
        userRepository.findAll()

    override fun saveUser(user: User): User {
        if (user.id != null && !userRepository.existsById(user.id!!)) {
            user.id = null
            user.profile?.id = null
        }
        return userRepository.save(user)
    }
}