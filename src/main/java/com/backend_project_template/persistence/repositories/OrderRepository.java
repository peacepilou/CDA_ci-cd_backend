package com.backend_project_template.persistence.repositories;
import com.backend_project_template.persistence.entities.OrderEntity;
import com.backend_project_template.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserEntity(UserEntity userEntity);
}
