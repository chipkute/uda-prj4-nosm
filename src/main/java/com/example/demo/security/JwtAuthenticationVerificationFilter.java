package com.example.demo.security;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.auth0.jwt.JWT;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationVerificationFilter extends BasicAuthenticationFilter {
    final String tokenSecret = "nosm_secret_token";

    public JwtAuthenticationVerificationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String token = req.getHeader("Authorization");

//        System.out.println("token: " + token);

        if (token == null || !token.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }


        UsernamePasswordAuthenticationToken authentication = getAuthInfo(req, token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthInfo(HttpServletRequest request, String token) {
        if (token == null) {
            return null;
        }

        String user = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();

        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }
        return null;

    }
}
