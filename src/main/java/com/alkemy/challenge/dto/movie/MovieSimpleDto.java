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
public class MovieSimpleDto {
    
    private Long movieId;
    private String movieTitle;
    private String movieImage;
    private LocalDate movieCreationDate;
    private Boolean movieDeleted;
}
