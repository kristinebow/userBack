package org.library.userback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
               .authorizeHttpRequests(authorizeRequests ->
                       authorizeRequests
                               .anyRequest().permitAll()
               )
               .formLogin(AbstractAuthenticationFilterConfigurer::permitAll
               )
               .headers(headers -> headers
                       .addHeaderWriter((request, response) ->
                               response.setHeader("X-Frame-Options", "SAMEORIGIN")))
               .httpBasic(withDefaults())
               .csrf(AbstractHttpConfigurer::disable // Disable CSRF protection if not using session-based authentication
               );

       return http.build();
   }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}