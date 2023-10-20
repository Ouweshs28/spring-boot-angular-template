package com.project.template;

import com.project.template.persistence.entity.UserEntity;
import com.project.template.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.project.template.persistence.enumeration.GenderEnum.FEMALE;
import static com.project.template.persistence.enumeration.GenderEnum.MALE;
import static com.project.template.persistence.enumeration.RoleEnum.USER;

/**
 * @author Ouweshs28
 */
@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        UserEntity userOuwesh = new UserEntity(1L, "ouweshs28", passwordEncoder.encode("password"), "ouwesh@email.com", "Ouwesh", "Seeroo", MALE, USER);
        UserEntity userSam = new UserEntity(2L, "sam", passwordEncoder.encode("password"), "sam@email.com", "Sam", "Johnstone", MALE, USER);
        UserEntity userRick = new UserEntity(3L, "rick", passwordEncoder.encode("password"), "rick@email.com", "Rick", "Allan", MALE, USER);
        UserEntity userHanaa = new UserEntity(4L, "hanaa", passwordEncoder.encode("password"), "hanaa@gmail.com", "Hanaa", "Azeria", FEMALE, USER);
        UserEntity userSara = new UserEntity(5L, "sara", passwordEncoder.encode("password"), "sara@email.com", "Sara", "Johnstone", FEMALE, USER);

        userRepository.saveAll(List.of(userOuwesh, userSam, userRick, userHanaa, userSara));
    }
}
