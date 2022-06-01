package com.openway.springtemplateapollo.controller;

import com.ecyrd.speed4j.StopWatch;
import com.openway.springtemplateapollo.builder.Response;
import com.openway.springtemplateapollo.constants.StatusApiConstants;
import com.openway.springtemplateapollo.constants.TokenEnum;
import com.openway.springtemplateapollo.entity.User;
import com.openway.springtemplateapollo.exception.ApolloTemplateException;
import com.openway.springtemplateapollo.payload.request.LoginRequest;
import com.openway.springtemplateapollo.payload.response.AuthenticateResponse;
import com.openway.springtemplateapollo.service.UserService;
import com.openway.springtemplateapollo.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticateController extends BaseController{
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @PostMapping(path = "/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest){
        StopWatch sw = new StopWatch();
        Response response;
        String svResponse;
        try{
            String email = loginRequest.getEmail();
            authenticate(email, loginRequest.getPassword());

            //generate token
            User user = userService.findUserByEmail(email);
            final String accessToken = jwtTokenUtils.generateToken(user.getId(), email, TokenEnum.ACCESS_TOKEN);
            final String refreshToken = jwtTokenUtils.generateToken(user.getId(), email, TokenEnum.REFRESH_TOKEN);

            //save refresh token for user
            userService.saveRefreshToken(email, refreshToken);

            //get response to client
            AuthenticateResponse loginResponse = new AuthenticateResponse(accessToken, refreshToken);
            response = new Response.Builder(StatusApiConstants.SUCCESS.getErrorCode(), StatusApiConstants.SUCCESS.getHttpStatusCode())
                    .buildMessage("login successfully")
                    .buildData(loginResponse)
                    .build();
            svResponse = gson.toJson(response);
            requestLogger.info("finish process request in {}", sw.stop());
        }catch (ApolloTemplateException e){
            e.printStackTrace();
            eLogger.error(e.getMessage());
            svResponse = buildFailureResponse(e.getCode(), e.getMessage());
        } catch (BadCredentialsException e){
            e.printStackTrace();
            eLogger.error("Bad credential exception: {}", e.getMessage());
            svResponse = buildFailureResponse(StatusApiConstants.INVALID_PARAM.getHttpStatusCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            eLogger.error("Another exception: {}", e.getMessage());
            svResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "an error occurred");
        }
        return ResponseEntity.ok(svResponse);
    }

    @PostMapping(path = "/refresh-token", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> refreshToken(@RequestParam String refreshToken){
        StopWatch sw = new StopWatch();
        Response response;
        String svResponse;
        try {
            if(jwtTokenUtils.validateToken(refreshToken)){
                int userId = jwtTokenUtils.getUserIdFromToken(refreshToken);
                User user = userService.findUserById(userId);
                if(refreshToken.equals(user.getRefreshToken())){
                    String email = jwtTokenUtils.getUsernameFromToken(refreshToken);
                    String accessToken = jwtTokenUtils.generateToken(userId, email, TokenEnum.ACCESS_TOKEN);
                    refreshToken = jwtTokenUtils.generateToken(userId, email, TokenEnum.REFRESH_TOKEN);

                    //update refresh token
                    userService.saveRefreshToken(email, refreshToken);

                    AuthenticateResponse authenticateResponse =
                            new AuthenticateResponse(accessToken, refreshToken);
                    response = new Response.Builder(StatusApiConstants.SUCCESS.getErrorCode(), StatusApiConstants.SUCCESS.getHttpStatusCode())
                            .buildMessage("refresh token successfully")
                            .buildData(authenticateResponse)
                            .build();
                }else {
                    response = new Response.Builder(StatusApiConstants.SUCCESS.getErrorCode(), StatusApiConstants.SUCCESS.getHttpStatusCode())
                            .buildMessage("Invalid refresh token")
                            .build();
                }
            }else {
                response = new Response.Builder(StatusApiConstants.OTHER_ERROR.getErrorCode(), StatusApiConstants.OTHER_ERROR.getHttpStatusCode())
                        .buildMessage("Refresh token has been expired")
                        .build();
            }
            svResponse = gson.toJson(response);
            requestLogger.info("finish process request in {}", sw.stop());
        } catch (ExpiredJwtException je) {
            je.printStackTrace();
            eLogger.error("Error refresh token: {}", je.getMessage());
            svResponse = buildFailureResponse(HttpStatus.UNAUTHORIZED.value(), "Refresh token has been expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(svResponse);
        } catch (Exception e){
            e.printStackTrace();
            eLogger.error("Another exception: {}", e.getMessage());
            svResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "an error occurred");
        }
        return ResponseEntity.ok(svResponse);
    }

    private void authenticate(String username, String password) throws AuthenticationException{
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
