package com.alkemy.challenge.respository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.challenge.models.Character;

@Repository
public interface CharacterRepository extends JpaRepository <Character, Long> {
    
    List<Character> findByName(String name);

    List<Character> findByAge(Integer age);

    List<Character> findByMoviesId(Long id);

    List<Character> findByWeight(Double weight);
}
