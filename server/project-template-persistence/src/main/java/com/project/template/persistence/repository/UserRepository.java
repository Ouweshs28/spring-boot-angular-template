package com.project.template.persistence.repository;

import com.project.template.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ouweshs28
 */
@Repository
public interface UserRepository extends AbstractRepository<UserEntity> {

    Optional<UserEntity> findByUsername(String username);

}
