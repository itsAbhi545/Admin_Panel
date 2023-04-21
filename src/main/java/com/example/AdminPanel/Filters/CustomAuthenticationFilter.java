package com.example.AdminPanel.Filters;

import com.example.AdminPanel.Config.JwtProvider;
import com.example.AdminPanel.Entity.Admin;
import com.example.AdminPanel.Entity.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(JwtProvider jwtProvider,AuthenticationManager authenticationManager) {
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //Map<String, String> jsonRequest = new ObjectMapper().readValue(inputStreamBytes, Map.class);
        ObjectMapper mapper = new ObjectMapper();
        String email,password;
        try {
            Admin admin = mapper.readValue(request.getInputStream(),Admin.class);
            email = admin.getEmail();
            password = admin.gtPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(email + ":" + password);
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(email,password);
        System.out.println(authenticationToken+"///");
        return authenticationManager.authenticate(authenticationToken);
        //return authenticationToken;
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //if authentication is sucessfull then i want to genereate an jsonwebtoken
        System.out.println("😎😎😎😎");
        Cookie cookie = new Cookie("uid",jwtProvider.generateToken("Abhijeet") );
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1*60);
        response.addCookie(cookie);
        new ObjectMapper().writeValue(response.getOutputStream(),jwtProvider.generateToken("Abhijeet"));
        //System.out.println( "\u001B[34m" + "control reaches here!!" + "\u001B[0m");
    }
}
