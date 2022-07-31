package com.alkemy.challenge.dto.movie;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieCreatedDto {
    
    private Long movieId;
    private String movieTitle;
    private Integer movieRating;
    private LocalDate movieCreationDate;
    private String movieImage;
    private Boolean movieDeleted;

}
