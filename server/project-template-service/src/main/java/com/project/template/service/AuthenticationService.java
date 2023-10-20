package com.project.template.service;

import com.project.template.model.AuthenticationRequestApiBean;
import com.project.template.model.AuthenticationResponseApiBean;
import com.project.template.model.UserCreateUpdateRequestApiBean;

/**
 * @author Ouweshs28
 */
public interface AuthenticationService {

    void registerUser(UserCreateUpdateRequestApiBean userCreateUpdateRequest);

    AuthenticationResponseApiBean loginUser(AuthenticationRequestApiBean authenticationRequest);
}
