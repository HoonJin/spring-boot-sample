package com.sample.sample.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@DynamicUpdate
@DynamicInsert
@Getter
@Setter
@ToString(callSuper = true, exclude = {"carts", "orders"})
public class User extends BaseEntity {

    @Column(unique = true)
    private String email;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user")
    private List<Cart> carts = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
