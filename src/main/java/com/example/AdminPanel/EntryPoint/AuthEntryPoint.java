package com.example.AdminPanel.EntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        for (final FieldError error : authException.getBindingResult().getFieldErrors()) {
//            errors += error.getDefaultMessage();
//            //errors.add(error.getField() + ": " + error.getDefaultMessage());
//        }
//        System.out.println("\u001B[34m" + "Authentication Entry Point!!!" + "\u001B[0m");
        Map<String, Object> map = new HashMap<>();
        System.out.println();
        map.put("message", authException.getLocalizedMessage());
        map.put("code", 401);
        map.put("msg", authException.getMessage());
        map.put("path", request.getServletPath());
        map.put("timestamp", System.currentTimeMillis());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
