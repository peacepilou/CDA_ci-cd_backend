package com.backend_project_template.persistence.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "`users`")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    @Setter
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;
    private String password;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders;

    public UserEntity(String email, String name, String password, List<OrderEntity> orders) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.orders = orders != null ? orders : new ArrayList<>();

        this.orders.forEach(order -> order.setUserEntity(this));
    }

}