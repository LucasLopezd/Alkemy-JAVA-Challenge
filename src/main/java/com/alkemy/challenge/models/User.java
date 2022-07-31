package com.alkemy.challenge.models;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import org.hibernate.annotations.SQLDelete;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET user_deleted = true WHERE user_id = ?")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;
    
    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_password")
    private String password;

    
    @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
        )
    private Set<Role> roles;

    @Column(name = "user_deleted")
    private Boolean deleted;
}
