package com.alkemy.challenge.dto.movie;

import java.time.LocalDate;
import java.util.List;

import com.alkemy.challenge.dto.character.CharacterSimpleDto;
import com.alkemy.challenge.dto.gender.GenderSimpleDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailedDto {
    
    private Long movieId;
    private String movieTitle;
    private Integer movieRating;
    private LocalDate movieCreationDate;
    private String movieHistory;
    private String movieImage;
    private Boolean movieDeleted;
    private List<CharacterSimpleDto> movieCharacters;
    private List<GenderSimpleDto> movieGenders;
}
