package com.zerava.closetapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.zerava.closetapi.service.CustomUserDetailsService;

import java.util.List;

/*✅ This does everything you need:

Enables cors() directly inside the HttpSecurity chain.

Configures it globally.

Works with Spring Security’s modern API.

Allows React (5173) to call backend (8080).
*/
@Configuration
public class SecurityConfig {
	
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ enable cors here
            .authorizeHttpRequests(auth -> auth
            		   .requestMatchers("/api/auth/**").permitAll()  // ✅ allow login/register
                       .requestMatchers("/api/**").authenticated()  // ✅ protect other APIs
            ); // for now basic auth; later replace with JWT
        return http.build();
    }

    

    @Bean
    public PasswordEncoder passwordEncoder() {
//    	return new BCryptPasswordEncoder();
    	return NoOpPasswordEncoder.getInstance(); // ⚠️ only for testing
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
    	return config.getAuthenticationManager();
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