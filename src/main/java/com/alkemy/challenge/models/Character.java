package com.alkemy.challenge.models;

import java.util.List;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.CascadeType.ALL;
import org.hibernate.annotations.SQLDelete;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "characters")
@SQLDelete(sql = "UPDATE characters SET character_deleted = true WHERE character_id = ?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Character {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "character_id")
    private Long id;

    @Column(name = "character_image")
    private String image;

    @Column(name = "character_name")
    private String name;

    @Column(name = "character_age")
    private Integer age;

    @Column(name = "character_weight")
    private Double weight;

    @Column(name = "character_history")
    private String history;

    @ManyToMany(fetch = LAZY, cascade = ALL)
        @JoinTable(
            name = "characters_movies",
            joinColumns = {@JoinColumn(name = "character_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "movie_id", nullable = false)})
    private List<Movie> movies;

    @Column(name = "character_deleted")
    private Boolean deleted;
}

