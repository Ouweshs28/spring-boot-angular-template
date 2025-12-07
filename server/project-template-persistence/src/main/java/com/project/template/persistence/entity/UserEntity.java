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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

}