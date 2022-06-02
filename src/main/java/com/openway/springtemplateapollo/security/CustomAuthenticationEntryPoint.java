package com.openway.springtemplateapollo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.openway.springtemplateapollo.builder.Response;
import com.openway.springtemplateapollo.constants.StatusApiConstants;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -7858869558944343875L;

    @Autowired
    @Qualifier("gsonCustom")
    private Gson gson;

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
            if(exception instanceof ExpiredJwtException){
                Map<String, Object> svResponse = new HashMap<>();
                svResponse.put("code", StatusApiConstants.UNAUTHORIZED.getErrorCode());
                svResponse.put("status", StatusApiConstants.UNAUTHORIZED.getHttpStatusCode());
                svResponse.put("message", "Token has been expired");
                body = new ObjectMapper().writeValueAsBytes(svResponse);
            }else if(exception instanceof BadCredentialsException) {
                Map<String, Object> svResponse = new HashMap<>();
                svResponse.put("code", StatusApiConstants.UNAUTHORIZED.getErrorCode());
                svResponse.put("status", StatusApiConstants.UNAUTHORIZED.getHttpStatusCode());
                svResponse.put("message", "Invalid token");
                body = new ObjectMapper().writeValueAsBytes(svResponse);
            }else {
                body =new ObjectMapper().writeValueAsBytes(Collections.singletonMap("cause", exception.toString()));
            }
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
