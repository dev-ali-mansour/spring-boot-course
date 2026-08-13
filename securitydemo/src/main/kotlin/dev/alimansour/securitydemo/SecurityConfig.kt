package dev.alimansour.securitydemo

import dev.alimansour.securitydemo.jwt.AuthEntryPointJwt
import dev.alimansour.securitydemo.jwt.AuthTokenFilter
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.sql.DataSource


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val dataSource: DataSource,
    private val unauthorizedHandler: AuthEntryPointJwt,
) {

    @Bean
    fun defaultSecurityFilterChain(
        http: HttpSecurity,
        authTokenFilter: AuthTokenFilter
    ): SecurityFilterChain {
        http.authorizeHttpRequests { requests ->
            requests
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/signin").permitAll()
                .anyRequest().authenticated()
        }
        http.sessionManagement { session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
//        http.formLogin(withDefaults())
//        http.httpBasic(withDefaults())
        http.exceptionHandling { exception ->
            exception.authenticationEntryPoint(unauthorizedHandler)
        }
        http.headers { headers ->
            headers.frameOptions { frameOptions ->
                frameOptions.sameOrigin()
            }
        }
        http.csrf { csrf -> csrf.disable() }

        http.addFilterBefore(
            authTokenFilter,
            UsernamePasswordAuthenticationFilter::class.java
        )
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService = JdbcUserDetailsManager(dataSource)

    @Bean
    fun initData(userDetailsService: UserDetailsService): CommandLineRunner {
        return CommandLineRunner {
            val userDetailsManager = userDetailsService as JdbcUserDetailsManager
            val user1 = User.withUsername("user1")
                .password(passwordEncoder().encode("password1"))
                .roles("USER")
                .build()
            val admin = User.withUsername("admin") //.password(passwordEncoder().encode("adminPass"))
                .password(passwordEncoder().encode("adminPass"))
                .roles("ADMIN")
                .build()

            userDetailsManager.createUser(user1)
            userDetailsManager.createUser(admin)
        }
    }


    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(builder: AuthenticationConfiguration): AuthenticationManager =
        builder.authenticationManager
}
