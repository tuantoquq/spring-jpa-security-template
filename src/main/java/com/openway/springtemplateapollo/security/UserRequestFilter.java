package com.openway.springtemplateapollo.security;

import com.openway.springtemplateapollo.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class UserRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            //get token from bearer token
            String jwtToken = jwtTokenUtils.extractJwtFromRequest(request);
            if (StringUtils.hasText(jwtToken) && jwtTokenUtils.validateToken(jwtToken)) {
                UserDetails userDetails = new User(jwtTokenUtils.getUsernameFromToken(jwtToken), "",
                        new ArrayList<>());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }catch (ExpiredJwtException | BadCredentialsException ex){
            request.setAttribute("exception", ex);
        }

        filterChain.doFilter(request, response);

    }
}
