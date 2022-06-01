package com.openway.springtemplateapollo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -7858869558944343875L;
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Exception exception =(Exception) request.getAttribute("exception");
        String message;
        byte[] body;
        if(exception != null){
            body =new ObjectMapper().writeValueAsBytes(Collections.singletonMap("cause", exception.toString()));
        }else {
            if(authException.getCause() != null){
                message = authException.getCause().toString() +" "+authException.getMessage();
            }else{
                message = authException.getMessage();
            }

            body =new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
        }
        response.getOutputStream().write(body);
    }
}
