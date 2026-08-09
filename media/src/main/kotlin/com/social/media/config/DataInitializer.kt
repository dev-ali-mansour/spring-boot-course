package com.social.media.config

import com.social.media.model.Group
import com.social.media.model.Post
import com.social.media.model.Profile
import com.social.media.model.User
import com.social.media.repository.GroupRepository
import com.social.media.repository.PostRepository
import com.social.media.repository.ProfileRepository
import com.social.media.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataInitializer(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val profileRepository: ProfileRepository,
    private val postRepository: PostRepository,
) {
    @Bean
    fun initializeData(): CommandLineRunner = CommandLineRunner {
        // Create some users
        val user1 = User()
        val user2 = User()
        val user3 = User()

        // Save users to the database
        userRepository.save(user1)
        userRepository.save(user2)
        userRepository.save(user3)

        // Create some groups
        val group1 = Group()
        val group2 = Group()

        // Add users to groups
        group1.users.add(user1)
        group1.users.add(user2)
        group2.users.add(user2)
        group2.users.add(user3)

        // Save groups to the database
        groupRepository.save(group1)
        groupRepository.save(group2)

        // Associate users with groups
        user1.groups.add(group1)
        user2.groups.add(group1)
        user2.groups.add(group2)
        user3.groups.add(group2)

        // Save users back to database to update association
        userRepository.save(user1)
        userRepository.save(user2)
        userRepository.save(user3)

        // Create some posts
        val post1 = Post()
        val post2 = Post()
        val post3 = Post()

        // Associate users with posts
        post1.user = user1
        post2.user = user2
        post3.user = user3

        // Save posts to the database
        postRepository.save(post1)
        postRepository.save(post2)
        postRepository.save(post3)

        // Create some profiles associated with users
        val profile1 = Profile()
        val profile2 = Profile()
        val profile3 = Profile()

        // Associate users with profiles
        profile1.user = user1
        profile2.user = user2
        profile3.user = user3


        // Save profiles to the database
        profileRepository.save(profile1)
        profileRepository.save(profile2)
        profileRepository.save(profile3)

    }
}
