package com.alkemy.challenge.mapper;

import java.io.Serializable;
import java.util.List;

import static java.util.stream.Collectors.toList;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.alkemy.challenge.dto.character.CharacterDto;
import com.alkemy.challenge.dto.movie.NewMovieDto;
import com.alkemy.challenge.models.Character;
import com.alkemy.challenge.models.Movie;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GenericModelMapper implements Serializable{

    private final ModelMapper mapper;

    public <S, D> D map(S sourceClass, Class<D> destinationClass) {
        return mapper.map(sourceClass, destinationClass);
    }

    public <S, D> List<D> mapAll(List<S> sourceList, Class<D> destinationClass) {
        return sourceList.stream()
                .map(e -> map(e, destinationClass))
                .collect(toList());
    }
    
    public Character mapSkippingMovies(CharacterDto dto){
        return mapper.typeMap(CharacterDto.class, Character.class)
                     .addMappings(m -> m.skip(Character::setMovies))
                     .map(dto);
    }

    public Character mapSkippingImage(CharacterDto dto){
        return mapper.typeMap(CharacterDto.class, Character.class)
                     .addMappings(m -> m.skip(Character::setImage))
                     .map(dto);
    }

    public Movie mapSkippingGendersAndCharacters(NewMovieDto dto){
        return mapper.typeMap(NewMovieDto.class, Movie.class)
                     .addMappings(m -> m.skip(Movie::setGenders))
                     .addMappings(m -> m.skip(Movie::setCharacters))
                     .map(dto);
    }
    
}
