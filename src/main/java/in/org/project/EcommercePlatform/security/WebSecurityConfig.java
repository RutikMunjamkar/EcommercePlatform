package in.org.project.EcommercePlatform.security;

import in.org.project.EcommercePlatform.type.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Autowired
    HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity
                .csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/login").hasAnyRole(RoleType.ADMIN.toString(),RoleType.CUSTOMER.toString(),RoleType.MERCHANT.toString())
                        .requestMatchers("/customer/**").hasRole(RoleType.CUSTOMER.toString())
                        .anyRequest().permitAll())
                .addFilterAfter(jwtAuthFilter, ExceptionTranslationFilter.class)
                .exceptionHandling(exceptionHandling-> exceptionHandling.accessDeniedHandler(((request, response, accessDeniedException) ->
                                handlerExceptionResolver.resolveException(request,response,handlerExceptionResolver,accessDeniedException)))
                                .authenticationEntryPoint(((request, response, authException) ->
                                        handlerExceptionResolver.resolveException(request,response,handlerExceptionResolver,authException))));

        return httpSecurity.build();
    }
}