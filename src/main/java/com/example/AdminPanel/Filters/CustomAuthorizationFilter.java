package com.example.AdminPanel.Filters;

import com.example.AdminPanel.Config.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    public CustomAuthorizationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //System.out.println(request.getServletPath()+"???");
        //
        //
        // System.out.println("\u001B[35m" + request.getServletPath() + "\u001B[0m");
        if(request.getServletPath().contains("/assets/")||request.getServletPath().equals("/login")
        ||request.getServletPath().contains("/lib/bootstrap/css/")
        ||request.getServletPath().equals("/")){
         //   System.out.println(request.getServletPath()+"///If");
            filterChain.doFilter(request,response);
        }
        else {

            if(request.getCookies()==null){
                String redirectUrl = "/";
                new DefaultRedirectStrategy().sendRedirect(request,response,redirectUrl);
                return;
            }
            String cookieValue = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("uid"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
            if(cookieValue==null||!jwtProvider.validateToken(cookieValue)){
                String redirectUrl = "/";
                new DefaultRedirectStrategy().sendRedirect(request,response,redirectUrl);
                return;
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(jwtProvider.getUsernameFromToken(cookieValue), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request, response);
        }
    }
}
