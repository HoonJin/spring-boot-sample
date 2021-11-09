package com.sample.sample.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "users")
@DynamicUpdate
@DynamicInsert
@Getter
@Setter
public class User extends BaseEntity {

    @Column(unique = true)
    private String email;
}
