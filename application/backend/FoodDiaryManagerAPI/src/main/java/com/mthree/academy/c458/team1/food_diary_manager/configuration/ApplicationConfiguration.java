package com.mthree.academy.c458.team1.food_diary_manager.configuration;

import com.mthree.academy.c458.team1.food_diary_manager.services.security.JwtService;
import com.mthree.academy.c458.team1.food_diary_manager.services.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationConfiguration {

    private final UserDetailsServiceImpl userDetailsService;

    public ApplicationConfiguration(UserDetailsServiceImpl userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //Stateless authentication for REST API
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);

        //Endpoint security configuration
        http.authorizeHttpRequests(request -> request

            //Public endpoints
            .antMatchers(
                "/api/auth/**"
            ).permitAll()

            //Role specific endpoints
            //...

            //Private endpoints
//            .anyRequest().permitAll() //Temporarily allow all requests
            .anyRequest().authenticated()
        );

        //JWT-based authentication, using JWT validation filter from oauth2
        http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

        //Use custom user details service implementation for authentication
        http.userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(JwtService jwtService) {
        return NimbusJwtDecoder
            .withSecretKey(jwtService.getSigningKey())
            .macAlgorithm(MacAlgorithm.HS256)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
