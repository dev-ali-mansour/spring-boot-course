package dev.alimansour.sbecom.security

import dev.alimansour.sbecom.model.AppRole
import dev.alimansour.sbecom.model.Role
import dev.alimansour.sbecom.model.User
import dev.alimansour.sbecom.repository.RoleRepository
import dev.alimansour.sbecom.repository.UserRepository
import dev.alimansour.sbecom.security.jwt.AuthEntryPointJwt
import dev.alimansour.sbecom.security.jwt.AuthTokenFilter
import dev.alimansour.sbecom.security.service.UserDetailsServiceImpl
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val userDetailsService: UserDetailsServiceImpl,
    private val unauthorizedHandler: AuthEntryPointJwt,
) {

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())

        return authProvider
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager =
        authConfig.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity, authJwtTokenFilter: AuthTokenFilter): SecurityFilterChain =
        http.csrf { csrf -> csrf.disable() }
            .exceptionHandling { exception -> exception.authenticationEntryPoint(unauthorizedHandler) }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/public/**").permitAll()
                    .requestMatchers("/api/admin/**").permitAll()
                    .requestMatchers("/api/test/**").permitAll()
                    .requestMatchers("/images/**").permitAll()
                    .anyRequest().authenticated()
            }
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authJwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .headers { headers ->
                headers.frameOptions { frameOptions ->
                    frameOptions.sameOrigin()
                }
            }
            .build()

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { authManager ->
            authManager.ignoring()
                .requestMatchers(
                    "/v2/api-docs/**",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**"
                )
        }
    }

    @Bean
    fun initData(
        roleRepository: RoleRepository,
        userRepository: UserRepository,
        passwordEncoder: PasswordEncoder
    ): CommandLineRunner {
        return CommandLineRunner {
            // Retrieve or create roles
            val userRole: Role = roleRepository.findByName(AppRole.ROLE_USER)
                .orElseGet({
                    val newUserRole: Role = Role(name = AppRole.ROLE_USER)
                    roleRepository.save(newUserRole)
                })

            val sellerRole: Role = roleRepository.findByName(AppRole.ROLE_SELLER)
                .orElseGet({
                    val newSellerRole: Role = Role(name = AppRole.ROLE_SELLER)
                    roleRepository.save(newSellerRole)
                })

            val adminRole: Role = roleRepository.findByName(AppRole.ROLE_ADMIN)
                .orElseGet({
                    val newAdminRole: Role = Role(name = AppRole.ROLE_ADMIN)
                    roleRepository.save(newAdminRole)
                })

            val userRoles: MutableSet<Role> = mutableSetOf(userRole)
            val sellerRoles: MutableSet<Role> = mutableSetOf(sellerRole)
            val adminRoles: MutableSet<Role> = mutableSetOf(userRole, sellerRole, adminRole)


            // Create users if not already present
            if (!userRepository.existsByUsername("user1")) {
                val user1 = User(
                    username = "user1",
                    email = "user1@example.com",
                    password = checkNotNull(passwordEncoder.encode("P@ss4user")) {
                        "Password encoding failed to generate a valid hash"
                    }
                )
                userRepository.save(user1)
            }

            if (!userRepository.existsByUsername("seller1")) {
                val seller1 = User(
                    username = "seller1",
                    email = "seller1@example.com",
                    password = checkNotNull(passwordEncoder.encode("P@ss4seller")) {
                        "Password encoding failed to generate a valid hash"
                    }
                )
                userRepository.save(seller1)
            }

            if (!userRepository.existsByUsername("admin")) {
                val admin = User(
                    username = "admin",
                    email = "admin@example.com",
                    password = checkNotNull(passwordEncoder.encode("P@ss4admin")) {
                        "Password encoding failed to generate a valid hash"
                    }
                )
                userRepository.save(admin)
            }

            // Update roles for existing users
            userRepository.findByUsername("user1").ifPresent { user ->
                user.roles = userRoles
                userRepository.save(user)
            }

            userRepository.findByUsername("seller1").ifPresent { seller ->
                seller.roles = sellerRoles
                userRepository.save(seller)
            }
            userRepository.findByUsername("admin").ifPresent { admin ->
                admin.roles = adminRoles
                userRepository.save(admin)
            }
        }
    }

}