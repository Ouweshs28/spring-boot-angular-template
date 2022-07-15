package com.project.template.service;

import com.project.template.model.PageApiBean;
import com.project.template.model.UserCreateUpdateRequestApiBean;
import org.springframework.data.domain.PageRequest;

/**
 * @author Ouweshs28
 */
public interface UserService {

    Long createUser(UserCreateUpdateRequestApiBean createUserRequest);

    void updateUser(UserCreateUpdateRequestApiBean userUpdateRequest);

    void deleteUser(Long userId);

    UserCreateUpdateRequestApiBean findUserById(Long userId);

    PageApiBean findAllUsers(String criteria, String gender, PageRequest pageRequest);

}
