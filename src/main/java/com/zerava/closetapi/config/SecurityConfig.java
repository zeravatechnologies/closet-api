package com.zerava.closetapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.List;

/*✅ This does everything you need:

Enables cors() directly inside the HttpSecurity chain.

Configures it globally.

Works with Spring Security’s modern API.

Allows React (5173) to call backend (8080).
*/
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ enable cors here
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/register").permitAll()
                .requestMatchers("/api/**").permitAll() // ✅ allow all your APIs for now
                .anyRequest().authenticated()
            );
        return http.build();
    }

    // ✅ Centralized CORS Configuration
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}