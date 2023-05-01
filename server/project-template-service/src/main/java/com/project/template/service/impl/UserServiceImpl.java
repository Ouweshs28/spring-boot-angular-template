package com.project.template.service.impl;

import com.project.template.exception.ResourceNotFoundException;
import com.project.template.mapper.PageMapper;
import com.project.template.mapper.UserMapper;
import com.project.template.model.PageApiBean;
import com.project.template.model.UserCreateUpdateRequestApiBean;
import com.project.template.persistence.entity.UserEntity;
import com.project.template.persistence.enumeration.GenderEnum;
import com.project.template.persistence.repository.UserRepository;
import com.project.template.service.UserService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.project.template.persistence.entity.QUserEntity.userEntity;

/**
 * @author Ouweshs28
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String USER_ID_NOT_FOUND = "UserId :%d not found";

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PageMapper pageMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PageMapper pageMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    public Long createUser(UserCreateUpdateRequestApiBean createUserRequest) {
        return userRepository.save(userMapper.mapToUserEntity(createUserRequest)).getId();
    }

    @Override
    public void updateUser(UserCreateUpdateRequestApiBean userUpdateRequest) {
        UserEntity user = userRepository.findById(userUpdateRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_ID_NOT_FOUND, userUpdateRequest.getId())));
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
    public UserCreateUpdateRequestApiBean findUserById(Long userId) {
        return userRepository.findById(userId).map(userMapper::mapToUserCreateOrUpdateRequest)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_ID_NOT_FOUND, userId)));
    }

    @Override
    public PageApiBean findAllUsers(String criteria, String gender, PageRequest pageRequest) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (Objects.nonNull(criteria)) {

            predicate.and(userEntity.firstName.concat(" ").concat(userEntity.lastName).containsIgnoreCase(criteria)).
                    or(userEntity.lastName.concat(" ").concat(userEntity.firstName).containsIgnoreCase(criteria));
        }

        if (EnumUtils.isValidEnum(GenderEnum.class, gender)) {
            predicate.and(userEntity.gender.eq(GenderEnum.valueOf(gender)));
        }

        Page<UserEntity> result = userRepository.findAll(predicate, pageRequest);

        return pageMapper.mapToUserCreateOrUpdateRequest(result);

    }

}