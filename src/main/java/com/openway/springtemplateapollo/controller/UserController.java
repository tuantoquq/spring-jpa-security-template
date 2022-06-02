package com.openway.springtemplateapollo.controller;

import com.ecyrd.speed4j.StopWatch;
import com.openway.springtemplateapollo.builder.Response;
import com.openway.springtemplateapollo.exception.ApolloTemplateException;
import com.openway.springtemplateapollo.service.UserService;
import com.openway.springtemplateapollo.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @GetMapping(path = "/profile")
    public ResponseEntity<String> getUserProfile(@RequestHeader("Authorization") String bearerToken){
        StopWatch sw = new StopWatch();
        Response response;
        String svResponse;
        try{
            String token = jwtTokenUtils.extractJwtFromBearerToken(bearerToken);
            int userId = jwtTokenUtils.getUserIdFromToken(token);
            response = userService.getUserProfile(userId);
            svResponse = gson.toJson(response);
            requestLogger.info("finish process request in {}", sw.stop());
        } catch (ApolloTemplateException e){
            e.printStackTrace();
            eLogger.error(e.getMessage());
            svResponse = buildFailureResponse(e.getCode(), e.getMessage());
        } catch (ExpiredJwtException je) {
            je.printStackTrace();
            eLogger.error("Error refresh token: {}", je.getMessage());
            svResponse = buildFailureResponse(HttpStatus.UNAUTHORIZED.value(), "Token has been expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(svResponse);
        } catch (Exception e){
            e.printStackTrace();
            eLogger.error("Another exception: {}", e.getMessage());
            svResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "an error occurred");
        }
        return ResponseEntity.ok(svResponse);
    }
}
