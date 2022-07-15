package com.project.template.rest;


import com.project.template.api.ApiApi;
import com.project.template.model.PageApiBean;
import com.project.template.model.UserCreateUpdateRequestApiBean;
import com.project.template.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author Ouweshs28
 */
@RestController
@Transactional
public class UserController implements ApiApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Long> createUser(UserCreateUpdateRequestApiBean userCreateRequestApiBean) {
        Long createdUserId = userService.createUser(userCreateRequestApiBean);
        return ResponseEntity.ok(createdUserId);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateUser(UserCreateUpdateRequestApiBean userUpdateRequestApiBean) {
        userService.updateUser(userUpdateRequestApiBean);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserCreateUpdateRequestApiBean> findUserById(Long userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @Override
    public ResponseEntity<PageApiBean> findAllUsers(String criteria, String gender, Integer pageNumber, Integer pageSize, String sortOrder, String sortBy) {
        Sort sort = Sort.by("DESC".equalsIgnoreCase(sortOrder) ? DESC : ASC, sortBy == null ? "firstName" : sortBy);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok(userService.findAllUsers(criteria, gender, pageRequest));
    }
}