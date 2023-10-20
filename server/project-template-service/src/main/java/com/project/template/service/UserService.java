package com.project.template.service;

import com.project.template.model.GenderEnumApiBean;
import com.project.template.model.PageApiBean;
import com.project.template.model.UserCreateUpdateRequestApiBean;
import com.project.template.model.UserResponseApiBean;
import com.project.template.persistence.entity.UserEntity;
import org.springframework.data.domain.PageRequest;

/**
 * @author Ouweshs28
 */
public interface UserService {

    Long createUser(UserCreateUpdateRequestApiBean createUserRequest);

    void updateUser(UserCreateUpdateRequestApiBean userUpdateRequest);

    void deleteUser(Long userId);

    UserResponseApiBean findUserById(Long userId);

    PageApiBean findAllUsers(String criteria, GenderEnumApiBean gender, PageRequest pageRequest);

    UserEntity findByUsername(String username);

}
