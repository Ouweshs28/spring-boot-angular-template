package com.project.template.rest;

import com.project.template.api.AuthApi;
import com.project.template.model.AuthenticationRequestApiBean;
import com.project.template.model.AuthenticationResponseApiBean;
import com.project.template.model.UserCreateUpdateRequestApiBean;
import com.project.template.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ouweshs28
 */
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthApi {

    private final AuthenticationService authenticationService;


    @Override
    public ResponseEntity<AuthenticationResponseApiBean> loginUser(AuthenticationRequestApiBean authenticationRequest) {
        return ResponseEntity.ok(authenticationService.loginUser(authenticationRequest));
    }

    @Override
    public ResponseEntity<Void> registerUser(UserCreateUpdateRequestApiBean userCreateUpdateRequest) {
        authenticationService.registerUser(userCreateUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
