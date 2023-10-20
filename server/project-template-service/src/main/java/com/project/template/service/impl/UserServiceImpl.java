package com.project.template.service.impl;

import com.project.template.exception.ResourceNotFoundException;
import com.project.template.mapper.PageMapper;
import com.project.template.mapper.UserMapper;
import com.project.template.model.GenderEnumApiBean;
import com.project.template.model.PageApiBean;
import com.project.template.model.UserCreateUpdateRequestApiBean;
import com.project.template.model.UserResponseApiBean;
import com.project.template.persistence.entity.UserEntity;
import com.project.template.persistence.enumeration.GenderEnum;
import com.project.template.persistence.repository.UserRepository;
import com.project.template.service.UserService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.project.template.persistence.entity.QUserEntity.userEntity;

/**
 * @author Ouweshs28
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_ID_NOT_FOUND = "UserId :%d not found";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final PageMapper pageMapper;

    @Override
    public Long createUser(UserCreateUpdateRequestApiBean createUserRequest) {
        createUserRequest.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        return userRepository.save(userMapper.mapToUserEntity(createUserRequest)).getId();
    }

    @Override
    public void updateUser(UserCreateUpdateRequestApiBean userUpdateRequest) {
        UserEntity user = userRepository.findById(userUpdateRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_ID_NOT_FOUND, userUpdateRequest.getId())));
        userUpdateRequest.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        userMapper.mapToUpdateUserEntity(user, userUpdateRequest);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_ID_NOT_FOUND, userId)));
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponseApiBean findUserById(Long userId) {
        return userRepository.findById(userId).map(userMapper::mapToUserResponse)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_ID_NOT_FOUND, userId)));
    }

    @Override
    public PageApiBean findAllUsers(String criteria, GenderEnumApiBean gender, PageRequest pageRequest) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (Objects.nonNull(criteria)) {

            predicate.and(userEntity.firstName.concat(" ").concat(userEntity.lastName).containsIgnoreCase(criteria)).
                    or(userEntity.lastName.concat(" ").concat(userEntity.firstName).containsIgnoreCase(criteria));
        }

        if (Objects.nonNull(gender)) {
            predicate.and(userEntity.gender.eq(GenderEnum.valueOf(gender.name())));
        }

        Page<UserEntity> result = userRepository.findAll(predicate, pageRequest);
        return pageMapper.mapToUserCreateOrUpdateRequest(result);

    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Username :%s not found", username)));
    }

}