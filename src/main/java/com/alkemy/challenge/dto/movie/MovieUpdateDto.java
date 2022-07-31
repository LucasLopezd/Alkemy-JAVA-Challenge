package com.alkemy.challenge.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieUpdateDto {
    
    private String movieTitle;
    private Integer movieRating;
}
