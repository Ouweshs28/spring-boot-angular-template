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
import com.project.template.persistence.enumeration.RoleEnum;
import com.project.template.persistence.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UserCreateUpdateRequestApiBean createUserRequest;

    @BeforeEach
    void setUp() {
        createUserRequest = new UserCreateUpdateRequestApiBean();
        createUserRequest.setId(1L);
        createUserRequest.setFirstName("Ouwesh");
        createUserRequest.setLastName("Seeroo");
        createUserRequest.setGender(GenderEnumApiBean.MALE);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
    }

    @Test
    void testCreateUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userMapper.mapToUserEntity(createUserRequest)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        Long userId = userServiceImpl.createUser(createUserRequest);

        assertEquals(userEntity.getId(), userId);
        verify(userMapper).mapToUserEntity(createUserRequest);
        verify(userRepository).save(userEntity);
    }

    @Test
    void testUpdateUserWhenExist() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        doNothing().when(userMapper).mapToUpdateUserEntity(any(UserEntity.class), any(UserCreateUpdateRequestApiBean.class));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        userServiceImpl.updateUser(createUserRequest);

        verify(userRepository).findById(createUserRequest.getId());
        verify(userMapper).mapToUpdateUserEntity(userEntity, createUserRequest);
        verify(userRepository).save(userEntity);
    }

    @Test
    void testUpdateUserWhenDoesNotExist() {
        UserCreateUpdateRequestApiBean updateUserRequest = new UserCreateUpdateRequestApiBean();
        updateUserRequest.setId(2L);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(2L);

        when(userRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("UserId :" + userEntity.getId() + " not found"));
        doNothing().when(userMapper).mapToUpdateUserEntity(any(UserEntity.class), eq(updateUserRequest));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.updateUser(updateUserRequest));

        verify(userRepository).findById(2L);
        verify(userMapper, never()).mapToUpdateUserEntity(userEntity, updateUserRequest);
        verify(userRepository, never()).save(userEntity);
    }

    @Test
    void deleteUserWhenExists() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        doNothing().when(userRepository).deleteById(anyLong());

        userServiceImpl.deleteUser(userEntity.getId());
        verify(userRepository).findById(userEntity.getId());
        verify(userRepository).deleteById(userEntity.getId());
    }

    @Test
    void deleteUserWhenDoesNotExist() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(userRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("UserId :" + userEntity.getId() + " not found"));
        doNothing().when(userRepository).deleteById(anyLong());

        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.deleteUser(userEntity.getId()));
        verify(userRepository).findById(userEntity.getId());
        verify(userRepository, never()).deleteById(userEntity.getId());
    }

    @Test
    void findUserByIdWhenExist() {
        UserResponseApiBean expectedUser = new UserResponseApiBean();
        expectedUser.setId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new UserEntity()));
        when(userMapper.mapToUserResponse(any(UserEntity.class))).thenReturn(expectedUser);

        UserResponseApiBean actualUser = userServiceImpl.findUserById(expectedUser.getId());

        assertEquals(expectedUser, actualUser);
        verify(userRepository).findById(expectedUser.getId());
        verify(userMapper).mapToUserResponse(any(UserEntity.class));
    }

    @Test
    void findUserByIdWhenDoesNotExist() {
        UserResponseApiBean expectedUser = new UserResponseApiBean();
        expectedUser.setId(1L);
        when(userRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("UserId :" + expectedUser.getId() + " not found"));
        when(userMapper.mapToUserResponse(any(UserEntity.class))).thenReturn(expectedUser);

        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.findUserById(expectedUser.getId()));
        verify(userRepository).findById(expectedUser.getId());
        verify(userMapper, never()).mapToUserResponse(any(UserEntity.class));
    }

    @Test
    void testFindAllUsers() {
        String criteria = "Seeroo";
        GenderEnumApiBean gender = GenderEnumApiBean.MALE;
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        List<UserEntity> userList = Arrays.asList(
                new UserEntity(1L, "ouweshs28", "password", "ouweshseeroo@gmail.com", "Ouwesh", "Seeroo", GenderEnum.MALE, RoleEnum.USER),
                new UserEntity(3L, "shaad28", "password", "shaadseeroo@gmail.com", "Shaad", "Seeroo", GenderEnum.MALE, RoleEnum.USER)
        );
        Page<UserEntity> pageResult = new PageImpl<>(userList, pageRequest, userList.size());


        // mock the userRepository and pageMapper
        when(userRepository.findAll(any(BooleanBuilder.class), any(PageRequest.class))).thenReturn(pageResult);
        when(pageMapper.mapToUserCreateOrUpdateRequest(pageResult)).thenReturn(new PageApiBean());

        // call the method being tested
        PageApiBean result = userServiceImpl.findAllUsers(criteria, gender, pageRequest);

        // verify the userRepository and pageMapper calls
        ArgumentCaptor<BooleanBuilder> predicateCaptor = ArgumentCaptor.forClass(BooleanBuilder.class);
        verify(userRepository).findAll(predicateCaptor.capture(), eq(pageRequest));
        verify(pageMapper).mapToUserCreateOrUpdateRequest(pageResult);

        // verify the result
        assertNotNull(result);
    }
}