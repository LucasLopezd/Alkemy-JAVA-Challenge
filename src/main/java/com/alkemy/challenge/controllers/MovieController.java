package com.alkemy.challenge.controllers;

import static org.springframework.http.HttpStatus.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alkemy.challenge.dto.movie.MovieCreatedDto;
import com.alkemy.challenge.dto.movie.MovieDetailedDto;
import com.alkemy.challenge.dto.movie.MovieSimpleDto;
import com.alkemy.challenge.dto.movie.MovieUpdateDto;
import com.alkemy.challenge.dto.movie.NewMovieDto;
import com.alkemy.challenge.mapper.GenericModelMapper;
import com.alkemy.challenge.models.Movie;
import com.alkemy.challenge.service.MovieService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final GenericModelMapper mapper;

    @PostMapping("/new")
    public ResponseEntity<MovieCreatedDto> create(@RequestParam("image") MultipartFile image,
                                                  @RequestParam("title") String title,
                                                  @RequestParam("rating") Integer rating,
                                                  @RequestParam("creationDate") 
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime creationDate) {      

        LocalDate date = creationDate.toLocalDate();                                           
        NewMovieDto dto = new NewMovieDto(title, rating, date);                                            
        Movie movie = movieService.create(dto, image);        
        return new ResponseEntity<>(mapper.map(movie, MovieCreatedDto.class), CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<MovieSimpleDto> update(@RequestParam(value = "id", required = true) Long id,
                                                 @RequestParam(value = "newImage", required = false) MultipartFile image, 
                                                 @RequestParam(value = "newTitle", required = false) String title,
                                                 @RequestParam(value = "newRating", required = false) Integer rating) {

        MovieUpdateDto dto = new MovieUpdateDto(title, rating);                                           
        Movie movie = movieService.update(dto, id, image);
        return new ResponseEntity<>(mapper.map(movie, MovieSimpleDto.class), OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MovieSimpleDto> delete(@PathVariable(value = "id") Long id) {
        movieService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieSimpleDto> getById(@PathVariable(value = "id") Long id) {
        Movie movie = movieService.getById(id);
        return new ResponseEntity<>(mapper.map(movie, MovieSimpleDto.class), OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieSimpleDto>> getAll() {
        return new ResponseEntity<>(mapper.mapAll(movieService.getAll(), MovieSimpleDto.class), OK);
    }

    @GetMapping("/all/detailed")
    public ResponseEntity<List<MovieDetailedDto>> getAllMoviesDetailed() {
        return new ResponseEntity<>(movieService.getAllMoviesDetailed(), OK);
    }

    @GetMapping(params = "title")
    public ResponseEntity<List<MovieSimpleDto>> getMovieByTitle(
            @RequestParam(value = "title", required = false) String title) {
        return (title.isEmpty()) 
                ? new ResponseEntity<>(mapper.mapAll(movieService.getAll(), MovieSimpleDto.class), OK)
                : new ResponseEntity<>(mapper.mapAll(movieService.getByTitle(title), MovieSimpleDto.class), OK);
    }

    @GetMapping(params = "gender")
    public ResponseEntity<List<MovieSimpleDto>> getMovieByGenderId(
            @RequestParam(value = "gender", required = false) Long genderId) {
        return (genderId < 1 || genderId == null)
                ? new ResponseEntity<>(mapper.mapAll(movieService.getAll(), MovieSimpleDto.class), OK)
                : new ResponseEntity<>(mapper.mapAll(movieService.getByGenderId(genderId), MovieSimpleDto.class), OK);
    }

    @GetMapping(params="order")
    public ResponseEntity<List<MovieSimpleDto>> getAllMoviesOrderByCreationDate(@RequestParam(value ="order", required = false) String order) {
        byte orderCheked = movieService.checkOrder(order);
        
        if(orderCheked == 1) return new ResponseEntity<>(mapper.mapAll(movieService.getAllOrderByCreationDateAsc(), MovieSimpleDto.class), OK);
        if(orderCheked == 2) return new ResponseEntity<>(mapper.mapAll(movieService.getAllByOrderByCreationDateDesc(), MovieSimpleDto.class), OK);
        
        return new ResponseEntity<>(mapper.mapAll(movieService.getAll(), MovieSimpleDto.class), OK);
    }
               
    @PostMapping("/{movieid}/add/character/{characterid}")
    public void addCharacterToMovie(@PathVariable(value = "characterid") Long characterid, @PathVariable(value = "movieid") Long movieid) {
        movieService.addCharacterToMovie(movieid, characterid);
    }

    @PostMapping("/{movieid}/add/gender/{genderid}")
    public void addGenderToMovie(@PathVariable(value = "genderid") Long genderid, @PathVariable(value = "movieid") Long movieid) {
        movieService.addGenderToMovie(movieid, genderid);
    }

    @PutMapping("/{movieid}/remove/character/{characterid}")
    public void removeCharacterFromMovie(@PathVariable(value = "characterid") Long characterId, @PathVariable(value = "movieid") Long movieId){
        movieService.removeCharacterFromMovie(movieId, characterId);
    }

    @PutMapping("/{movieid}/remove/gender/{genderids}")
    public void removeGenderFromMovie(@PathVariable(value = "genderids") Long genderId, @PathVariable(value = "movieid") Long movieId){
        movieService.removeGenderFromMovie(movieId, genderId);
    }
}
