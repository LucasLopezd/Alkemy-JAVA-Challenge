package com.alkemy.challenge.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;
import org.hibernate.annotations.SQLDelete;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movies")
@SQLDelete(sql = "UPDATE movies SET movie_deleted = true WHERE movie_id = ?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(name = "movie_image")
    private String image;

    @Column(name = "movie_title")
    private String title;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "movie_creation_date")
    private LocalDate creationDate;

    @Column(name = "movie_rating")
    private Integer rating;

    @ManyToMany(fetch= LAZY, mappedBy = "movies")
    @Column(name = "movie_characters")
    private List<Character> characters;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "movies_genders",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "gender_id")          
            )
    private List<Gender> genders;

    @Column(name = "movie_deleted")
    private Boolean deleted;
}
