package com.project.template.dto;

import com.project.template.persistence.enumeration.GenderEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ouweshs28
 */
@Getter
@Setter
public class CreateUpdateUserRequest {

    private Long id;

    private String firstName;

    private String lastName;

    private GenderEnum genderEnum;

}
