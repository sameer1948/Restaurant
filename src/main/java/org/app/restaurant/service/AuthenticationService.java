package org.app.restaurant.service;

import org.app.restaurant.dto.AuthenticationRequest;
import org.app.restaurant.dto.AuthenticationResponse;
import org.app.restaurant.model.CustomUser;
import org.app.restaurant.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationService {

    @Autowired
    private CustomUserRepository customUserRepository;
    @Autowired
    private JwtService jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getKey()));
            CustomUser currentUser = customUserRepository.findByUsername(authenticationRequest.getUsername())
                    .orElseThrow(()-> new UsernameNotFoundException("user : " + authenticationRequest.getUsername() + " Not Existing..!"));
            String token = jwtUtils.generateToken(currentUser);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), currentUser);

           return AuthenticationResponse.builder()
                    .statusCode(HttpStatus.ACCEPTED.value())
                    .token(token)
                    .refreshToken(refreshToken)
                    .role(currentUser.getRoles())
                    .expirationTime("24 Hours")
                    .message("User : " + currentUser.getUsername() + " Authenticated .. !")
                    .build();

        } catch (DisabledException | LockedException | BadCredentialsException exception) {
            return AuthenticationResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(exception.getMessage())
                    .build();
        }

    }

    public AuthenticationResponse getRefreshToken(AuthenticationRequest authenticationRequest) {
        return AuthenticationResponse.builder().build();
        /*try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            CustomUser users = customUserRepository.findByUsername(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }*/
    }

}
