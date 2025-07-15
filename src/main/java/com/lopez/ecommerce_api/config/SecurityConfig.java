package com.lopez.ecommerce_api.config;

import com.lopez.ecommerce_api.config.filter.JwtFilter;
import com.lopez.ecommerce_api.repository.UserRepository;
import com.lopez.ecommerce_api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //private final UserRepository userRepository;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http/*, AuthenticationManager authManager, JwtService jwtService*/) throws Exception {
        //CustomAuthenticationFilter customAuthFilter = new CustomAuthenticationFilter(authManager, jwtService);
        //customAuthFilter.setFilterProcessesUrl("/api/login");
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/refresh-token", "/api/auth/register").permitAll()
                        // .requestMatchers(HttpMethod.DELETE, "/api/cart/itemId/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .anyRequest().authenticated())
                        //.requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole("ADMIN", "CUSTOMER"))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.addFilter(customAuthFilter)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

   /* @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userRepository::findByUsername;
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
