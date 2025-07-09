package com.backend_project_template.persistence.repositories;
import com.backend_project_template.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String userEmail);

    boolean existsByEmail(String email);
}