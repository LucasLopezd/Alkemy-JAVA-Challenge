package com.alkemy.challenge.models;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import org.hibernate.annotations.SQLDelete;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "genders")
@SQLDelete(sql = "UPDATE genders SET gender_deleted = true WHERE gender_id = ?")
@Getter
@Setter
public class Gender {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "gender_id")
    private Long id;

    @Column(name = "gender_name")
    private String name;

    @Column(name = "gender_image")
    private String image;

    @ManyToMany(mappedBy = "genders")
    private List<Movie> movies;

    @Column(name = "gender_deleted")
    private Boolean deleted;
}
