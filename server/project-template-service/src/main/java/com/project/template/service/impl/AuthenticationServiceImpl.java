package com.project.template.service.impl;

import com.project.template.model.AuthenticationRequestApiBean;
import com.project.template.model.AuthenticationResponseApiBean;
import com.project.template.model.UserCreateUpdateRequestApiBean;
import com.project.template.persistence.entity.UserEntity;
import com.project.template.service.AuthenticationService;
import com.project.template.service.JwtService;
import com.project.template.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * @author Ouweshs28
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void registerUser(UserCreateUpdateRequestApiBean userCreateUpdateRequest) {
        userService.createUser(userCreateUpdateRequest);
    }

    @Override
    public AuthenticationResponseApiBean loginUser(AuthenticationRequestApiBean authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        UserEntity user = userService.findByUsername(authenticationRequest.getUsername());
        String token = jwtService.generateToken(user.getUsername());
        return getAuthenticationResponseFromToken(token);
    }

    private AuthenticationResponseApiBean getAuthenticationResponseFromToken(String token) {
        AuthenticationResponseApiBean authenticationResponse = new AuthenticationResponseApiBean();
        authenticationResponse.setToken(token);
        return authenticationResponse;
    }
}
