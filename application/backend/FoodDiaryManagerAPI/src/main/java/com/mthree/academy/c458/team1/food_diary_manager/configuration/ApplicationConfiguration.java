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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

        //Enable CORS from React frontend
        http.cors(Customizer.withDefaults());

        //Endpoint security configuration
        http.authorizeHttpRequests(request -> request

            //Public endpoints
            .antMatchers(
                "/api/auth/**"
            ).permitAll()

            //Role specific endpoints
            //...

            //Private endpoints
            .anyRequest().permitAll() //Temporarily allow all requests
//            .anyRequest().authenticated()
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

    /**
     * Configuration to allow Cross Origin Resource Sharing with external React frontend.
     * Refreshing documentation <a href="https://www.baeldung.com/spring-cors"></a>
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //Only requests front react frontend allowed.
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        //All different request methods allowed.
        corsConfiguration.addAllowedMethod("*");
        //Allow credentials to be provided in the header (e.g. auth jwt).
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");

        //Use this CORS configuration for all requests.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
