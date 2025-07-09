package com.backend_project_template.persistence.repositories;

import com.backend_project_template.persistence.entities.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String userEmail);

  boolean existsByEmail(String email);
}
