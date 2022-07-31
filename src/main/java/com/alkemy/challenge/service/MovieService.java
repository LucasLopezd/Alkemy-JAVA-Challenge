package com.alkemy.challenge.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alkemy.challenge.dto.character.CharacterSimpleDto;
import com.alkemy.challenge.dto.gender.GenderSimpleDto;
import com.alkemy.challenge.dto.movie.MovieDetailedDto;
import com.alkemy.challenge.dto.movie.MovieUpdateDto;
import com.alkemy.challenge.dto.movie.NewMovieDto;
import com.alkemy.challenge.mapper.GenericModelMapper;
import com.alkemy.challenge.models.Character;
import com.alkemy.challenge.models.Gender;
import com.alkemy.challenge.models.Movie;
import com.alkemy.challenge.respository.CharacterRepository;
import com.alkemy.challenge.respository.GenderRepository;
import com.alkemy.challenge.respository.MovieRepository;
import com.alkemy.challenge.util.MovieServiceStruct;

import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class MovieService implements Serializable, MovieServiceStruct {

    private final MovieRepository movieRepository;
    private final CharacterRepository characterRepository;
    private final GenderRepository genderRepository;
    private final ImageService imageService;
    private final GenericModelMapper mapper;

    @Override
    public Movie create(NewMovieDto dto, MultipartFile image) {
        Movie movie = mapper.mapSkippingGendersAndCharacters(dto);
        String stringImage = imageService.imageToString(image);

        movie.setDeleted(false);
        movie.setImage(stringImage);
        return movieRepository.save(movie);
    }

    @Override
    public Movie update(MovieUpdateDto dto, Long  id, MultipartFile image) {
        Movie movie = checkAndSetAtributesForUpdate(dto, id, image);

        return movieRepository.save(movie);
    }

    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public Movie getById(Long id) {
        return movieRepository.findById(id).get();
    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> getByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Override
    public List<Movie> getAllByIds(List<Long> movieIds) {
        return movieRepository.findAllById(movieIds);
    }

    @Override
    public List<Movie> getAllOrderByCreationDateAsc() {
        return movieRepository.findAllByOrderByCreationDateAsc();
    }

    @Override
    public List<Movie> getAllByOrderByCreationDateDesc() {
        return movieRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public List<Movie> getByGenderId(Long genderId) {
        return movieRepository.findByGendersId(genderId);
    }

    @Override
    public void addGenderToMovie(Long movieId, Long genderId) {
        Movie movie = getById(movieId);   
        Gender gender = genderRepository.findById(genderId).get();    

        gender.getMovies().add(movie);
        movie.getGenders().add(gender);

        genderRepository.save(gender);
        movieRepository.save(movie);
    }

    @Override
    public void removeGenderFromMovie(Long movieId, Long genderId) {
        Movie movie = getById(movieId);
        Gender gender = genderRepository.findById(genderId).get();

        gender.getMovies().remove(movie);
        movie.getGenders().remove(gender);

        genderRepository.save(gender);
        movieRepository.save(movie);
    }

    @Override
    public void addCharacterToMovie(Long movieId, Long characterId) {
        Movie movie = getById(movieId);
        Character character = characterRepository.findById(characterId).get();    

        character.getMovies().add(movie);
        movie.getCharacters().add(character);

        characterRepository.save(character);
        movieRepository.save(movie);
    }

    @Override
    public void removeCharacterFromMovie(Long movieId, Long characterId) {
        Movie movie = getById(movieId);
        Character character = characterRepository.findById(characterId).get();

        character.getMovies().remove(movie);
        movie.getCharacters().remove(character);

        characterRepository.save(character);
        movieRepository.save(movie);
    }

    @Override
    public Movie checkAndSetAtributesForUpdate(MovieUpdateDto dto, Long id, MultipartFile image) {
        Movie movie = getById(id);

        if(!movie.getTitle().equals(dto.getMovieTitle()) && !dto.getMovieTitle().isEmpty()){
            movie.setTitle(dto.getMovieTitle());
        }
        if(movie.getRating() != dto.getMovieRating() && dto.getMovieRating() > 0 && dto.getMovieRating() < 6 && dto.getMovieRating() != null){
            movie.setRating(dto.getMovieRating());
        }
        if(!image.getOriginalFilename().isEmpty()){
            String imageString = imageService.imageToString(image);
            movie.setImage(imageString);
        }
        return movie;
    }

    public List<MovieDetailedDto> getAllMoviesDetailed(){
        List<Movie> movies = getAll();
        
        List<MovieDetailedDto> moviesDetailed = mapper.mapAll(movies, MovieDetailedDto.class);

        for (int i = 0; i < moviesDetailed.size(); i++) {
            moviesDetailed.get(i).setMovieCharacters(mapper.mapAll(
                movies.get(i).getCharacters(), CharacterSimpleDto.class));

            moviesDetailed.get(i).setMovieGenders(mapper.mapAll(
                movies.get(i).getGenders(), GenderSimpleDto.class));    
        }
        return moviesDetailed;
    }

    public byte checkOrder(String order) {
        if (order.equalsIgnoreCase("ASC"))
            return 1;
        if (order.equalsIgnoreCase("DESC"))
            return 2;
        return 0;
    }

    

}
