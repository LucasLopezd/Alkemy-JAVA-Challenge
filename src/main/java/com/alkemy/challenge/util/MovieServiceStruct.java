package com.alkemy.challenge.util;

import java.util.List;

import com.alkemy.challenge.dto.movie.MovieUpdateDto;
import com.alkemy.challenge.dto.movie.NewMovieDto;
import com.alkemy.challenge.models.Movie;

public interface MovieServiceStruct extends BasicCrud<Movie, MovieUpdateDto, NewMovieDto>{

    List<Movie> getAllByIds(List<Long> movieIds);

    List<Movie> getByTitle(String title);

    List<Movie> getAllOrderByCreationDateAsc();

    List<Movie> getAllByOrderByCreationDateDesc();

    List<Movie> getByGenderId(Long genderId);

    void addGenderToMovie(Long movieId, Long genderId);

    void removeGenderFromMovie(Long movieId, Long genderId);

    void addCharacterToMovie(Long movieId, Long characterId);

    void removeCharacterFromMovie(Long movieId, Long characterId);
}
