package com.project.template.service;

import com.project.template.dto.CreateUpdateUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author Ouweshs28
 */
public interface UserService {

    Long createUser(CreateUpdateUserRequest createUserRequest);

    void updateUser(CreateUpdateUserRequest userUpdateRequest);

    void deleteUser(Long userId);

    CreateUpdateUserRequest findUserById(Long userId);

    Page<CreateUpdateUserRequest> findAllUsers(String criteria, String gender, PageRequest pageRequest);

}
