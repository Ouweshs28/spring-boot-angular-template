package com.project.template.persistence.entity;

import com.project.template.persistence.enumeration.GenderEnum;
import com.project.template.persistence.enumeration.RoleEnum;
import jakarta.persistence.*;
import lombok.*;


/**
 * @author Ouweshs28
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "template_user")
public class UserEntity extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

}