package com.bank.app

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .httpBasic(withDefaults())
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/contacts/public/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/contacts").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/contacts").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/contacts/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }

        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val admin = User
            .withUsername("admin")
            .password(passwordEncoder().encode("admin123"))
            .roles("ADMIN")
            .build()
        val user = User
            .withUsername("user")
            .password(passwordEncoder().encode("user123"))
            .roles("USER")
            .build()

        return InMemoryUserDetailsManager(admin, user)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}