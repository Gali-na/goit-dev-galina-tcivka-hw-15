package com.example;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;


@RequiredArgsConstructor
@Service
public class CustomAuthProvider implements AuthenticationProvider {
    private final СustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String password = authentication.getCredentials().toString();
        String userName = authentication.getName();


        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
       return checkPassword(userDetails,password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return   UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);

    }

    private Authentication checkPassword(UserDetails userDetails, String rawPassword) {
        if(Objects.equals(userDetails.getPassword(),rawPassword)){
            return  new  UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),
                    userDetails.getAuthorities());

        }else {
            throw new BadCredentialsException("Invalid password");
        }
    }

}
