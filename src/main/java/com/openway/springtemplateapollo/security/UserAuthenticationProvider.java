package com.openway.springtemplateapollo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String email = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        if(email.isEmpty()){
            throw new BadCredentialsException("Invalid login email");
        }

        //get user details
        UserDetails user;
        try{
            user = userDetailsService.loadUserByUsername(email);
            if(!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())){
                throw new BadCredentialsException("Email or password is invalid");
            }
        }catch (UsernameNotFoundException e){
            throw new BadCredentialsException("Email not found");
        }
        return createSuccessfulAuthentication(authentication, user);
    }

    private Authentication createSuccessfulAuthentication(final Authentication authentication, final UserDetails user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), authentication.getCredentials(), user.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    public boolean supports(Class < ? > authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
