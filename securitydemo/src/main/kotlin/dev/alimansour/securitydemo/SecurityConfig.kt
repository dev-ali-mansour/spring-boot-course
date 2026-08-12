package dev.alimansour.securitydemo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { requests -> requests.anyRequest().authenticated() }
        http.sessionManagement { session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
//        http.formLogin(withDefaults())
        http.httpBasic(withDefaults())
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user1 = User.withUsername("user1")
            .password("{noop}password1")
            .roles("USER")
            .build()
        val admin = User.withUsername("admin")
            .password("{noop}adminPass")
            .roles("ADMIN")
            .build()
        return InMemoryUserDetailsManager(user1, admin)
    }
}
