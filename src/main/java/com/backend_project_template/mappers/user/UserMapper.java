package com.backend_project_template.mappers.user;

import com.backend_project_template.domain.User;
import com.backend_project_template.mappers.order.OrderMapper;
import com.backend_project_template.persistence.entities.OrderEntity;
import com.backend_project_template.persistence.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record UserMapper() {

    public static User toDomain(UserEntity userEntity) {
        User user = new User(userEntity.getId(), userEntity.getName(), userEntity.getEmail());

        if (userEntity.getOrders() != null) {
            userEntity.getOrders().forEach(orderEntity ->
                    user.getOrders().add(OrderMapper.toDomain(orderEntity, user))
            );
        }

        return user;
    }


    public static UserEntity toEntity(User user) {
        List<OrderEntity> orderEntities = user.getOrders().stream()
                .map(OrderMapper::toEntity)
                .collect(Collectors.toCollection(ArrayList::new));
        UserEntity userEntity = new UserEntity(user.getEmail(), user.getName(), user.getPassword(), orderEntities);
        userEntity.setId(user.getId());

        return userEntity;
    }

}
