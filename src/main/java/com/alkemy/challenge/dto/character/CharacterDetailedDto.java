package com.alkemy.challenge.dto.character;


import java.util.List;

import com.alkemy.challenge.dto.movie.MovieSimpleDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDetailedDto {
    
    private Long characterId;
    private String characterName;
    private Integer characterAge;
    private Double characterWeight;
    private String characterHistory;
    private String characterImage;
    private Boolean characterDeleted;
    private List<MovieSimpleDto> characterMovies;
}
