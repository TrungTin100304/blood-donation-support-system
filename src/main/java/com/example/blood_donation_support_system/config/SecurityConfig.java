package com.example.blood_donation_support_system.config;

import com.example.blood_donation_support_system.filter.CustomSecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomSecurityFilter filter,  CorsConfigurationSource corsConfigurationSource)throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(ss -> ss.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> {
                    // giúp định nghĩa quyền truy cập cho các link
                    request.requestMatchers(HttpMethod.POST, "/api/updateProfile/update").permitAll();

                    request.requestMatchers("/api/login",  "/api/register").permitAll();
                    request.requestMatchers(("/api/auth/**")).permitAll();
                    request.requestMatchers(HttpMethod.POST, "/api/register/admin", "/api/register/staff").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.POST, "/api/donor/register").hasAnyRole("MEMBER", "ADMIN","STAFF");

                    request.requestMatchers(HttpMethod.GET, "/api/matching/**").hasAnyRole("MEMBER", "ADMIN","STAFF");
                    request.requestMatchers(HttpMethod.GET, "/api/emergency/**").hasAnyRole("MEMBER", "ADMIN","STAFF");
                    request.requestMatchers( "/api/article/**").permitAll();


                    request.anyRequest().authenticated();
                })
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
