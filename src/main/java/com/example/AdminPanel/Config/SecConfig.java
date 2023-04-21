package com.example.AdminPanel.Config;

import com.example.AdminPanel.Filters.CustomAuthenticationFilter;
import com.example.AdminPanel.Filters.CustomAuthorizationFilter;
import org.springframework.boot.autoconfigure.session.DefaultCookieSerializerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecConfig {
    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtProvider jwtProvider;

    public SecConfig(UserDetailsService userDetailsService, AuthenticationEntryPoint authenticationEntryPoint, JwtProvider jwtProvider) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtProvider = jwtProvider;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(jwtProvider,authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)));
        filter.setFilterProcessesUrl("/login");
        http.csrf().disable();//withDefaults()//110001000
        //http://f289-112-196-113-2.in.ngrok.io/accounts/google/login/callback/
        http.cors().disable();
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().requestMatchers("/assets/**","/login","/lib/bootstrap/css/***","/").permitAll();
        http.authorizeHttpRequests().requestMatchers("/drivers","/dashboard").authenticated();
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.addFilter(filter);
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        //http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        //http.addFilterBefore(new AutzFactor(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CustomAuthorizationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    //This is must if we want to stop generating security password by system!!!
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
};
