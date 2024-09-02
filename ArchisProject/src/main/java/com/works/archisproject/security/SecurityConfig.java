package com.works.archisproject.security;

import com.works.archisproject.service.impl.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *
     * @param http
     * @return
     * @throws Exception
     *
     * @implNote https://www.baeldung.com/spring-security-session ile detaylara bakabiliriz.
     *
     * @code addFilterBefore(jwtAuthFilter, ) -> önce bu filtreden geçir eğer olumsuz dönerse authentication işlemi yap. Generate işlemi yap token dönecek onunla işlem yap
     *
     */

    // http://localhost:8080/swagger-ui/index.html#/ -> swagger a bağlanmak için
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x ->
                        x.requestMatchers( "api/v1/auth/**",
                                "/api/v1/skill/**","/api/v1/application/**",
                                "/swagger-ui/**","/v3/api-docs/**","/v2/api-docs/**","/swagger-resources/**").permitAll()
                )
                /*.authorizeHttpRequests(x ->
                        x.requestMatchers("/auth/user").authenticated()
                                .requestMatchers("/auth/admin").hasRole("ADMIN")
                )*/

                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(1)
                        .sessionRegistry(getsessionRegistry()))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    /**
     *
     * @code Gelen authentication requesti tokena çevirmeden önce authentication işlemini yapmamızı sağlar.
     *
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SessionRegistry getsessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
