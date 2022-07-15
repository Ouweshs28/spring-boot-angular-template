package com.project.template.mapper;

import com.project.template.model.PageApiBean;
import com.project.template.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

/**
 * @author Ouweshs28
 */
@Mapper(componentModel = "spring")
public interface PageMapper {

    PageApiBean mapToUserCreateOrUpdateRequest(Page<UserEntity> pageResponse);

}