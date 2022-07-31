package com.alkemy.challenge.util;

import java.util.List;

import com.alkemy.challenge.dto.character.CharacterDto;
import com.alkemy.challenge.models.Character;

public interface CharacterServiceStruct extends BasicCrud<Character, CharacterDto, CharacterDto> {

    List<Character> getAllByIds(List<Long> characterIds);

    List<Character> getByName(String name);

    List<Character> getByAge(Integer age);

    List<Character> getByWeight(Double weight);

    List<Character> getByMovieId(Long movieId);

    void addMovies(Long characterId, List<Long> moviesIds);

    void removeMovies(Long characterId, List<Long> moviesIds);
}
