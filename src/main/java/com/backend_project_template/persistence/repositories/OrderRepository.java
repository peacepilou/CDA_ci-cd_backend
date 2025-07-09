package com.backend_project_template.persistence.repositories;

import com.backend_project_template.persistence.entities.OrderEntity;
import com.backend_project_template.persistence.entities.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
  List<OrderEntity> findByUserEntity(UserEntity userEntity);
}
