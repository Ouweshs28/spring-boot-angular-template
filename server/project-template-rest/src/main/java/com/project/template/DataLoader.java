package com.project.template;

import com.project.template.persistence.entity.UserEntity;
import com.project.template.persistence.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.project.template.persistence.enumeration.GenderEnum.FEMALE;
import static com.project.template.persistence.enumeration.GenderEnum.MALE;

/**
 * @author Ouweshs28
 */
@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        UserEntity userOuwesh = new UserEntity(null, "Ouwesh", "Seeroo", MALE);
        UserEntity userSam = new UserEntity(null, "Sam", "Johnstone", MALE);
        UserEntity userRick = new UserEntity(null, "Rick", "Allan", MALE);
        UserEntity userHanaa = new UserEntity(null, "Hanaa", "Azeria", FEMALE);
        UserEntity userSara = new UserEntity(null, "Sara", "Johnstone", FEMALE);

        userRepository.saveAll(List.of(userOuwesh, userSam, userRick, userHanaa, userSara));
    }
}
