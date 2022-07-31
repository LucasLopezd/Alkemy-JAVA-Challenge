package com.alkemy.challenge.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.challenge.models.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{
    
    List<Movie> findByTitle(String title);

    List<Movie> findByGendersId(Long genreId);

    List<Movie> findAllByOrderByCreationDateAsc();

    List<Movie> findAllByOrderByCreationDateDesc();
}
