package com.project.template.mapper;

import com.project.template.dto.CreateUpdateUserRequest;
import com.project.template.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author Ouweshs28
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity mapToUserEntity(CreateUpdateUserRequest createUserRequest);

    CreateUpdateUserRequest mapToUserCreateOrUpdateRequest(UserEntity user);

    void mapToUpdateUserEntity(@MappingTarget UserEntity user, CreateUpdateUserRequest userUpdateRequest);

}