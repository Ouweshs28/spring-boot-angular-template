package com.project.template.persistence.entity;

import com.project.template.persistence.enumeration.GenderEnum;
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

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

}