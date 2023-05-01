package com.project.template.rest;


import com.project.template.dto.CreateUpdateUserRequest;
import com.project.template.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author Ouweshs28
 */
@RestController
@Transactional
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(CreateUpdateUserRequest userCreateRequest) {
        Long createdUserId = userService.createUser(userCreateRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(createdUserId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping({"/{userId}"})
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }


    @PutMapping
    public ResponseEntity<Void> updateUser(CreateUpdateUserRequest userCreateRequest) {
        userService.updateUser(userCreateRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping({"/{userId}"})
    public ResponseEntity<CreateUpdateUserRequest> findUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @GetMapping
    public ResponseEntity<Page<CreateUpdateUserRequest>> findAllUsers(@RequestParam(required = false) String criteria,
                                                                      @RequestParam(required = false) String gender,
                                                                      @RequestParam(defaultValue = "0") Integer pageNumber,
                                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                                      @RequestParam(required = false) String sortOrder,
                                                                      @RequestParam(required = false) String sortBy) {
        Sort sort = Sort.by("DESC".equalsIgnoreCase(sortOrder) ? DESC : ASC, sortBy == null ? "firstName" : sortBy);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok(userService.findAllUsers(criteria, gender, pageRequest));
    }
}