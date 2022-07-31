package com.alkemy.challenge.service;


import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alkemy.challenge.dto.character.CharacterDetailedDto;
import com.alkemy.challenge.dto.character.CharacterDto;
import com.alkemy.challenge.dto.movie.MovieSimpleDto;
import com.alkemy.challenge.mapper.GenericModelMapper;
import com.alkemy.challenge.models.Character;
import com.alkemy.challenge.respository.CharacterRepository;
import com.alkemy.challenge.respository.MovieRepository;
import com.alkemy.challenge.util.CharacterServiceStruct;

import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class CharacterService implements Serializable, CharacterServiceStruct {

    private final CharacterRepository characterRepository;
    private final MovieRepository movieRepository;
    private final ImageService imageService;
    private final GenericModelMapper mapper;

    @Override
    public Character create(CharacterDto dto, MultipartFile image) {
        Character character = mapper.mapSkippingMovies(dto);
        String stringImage = imageService.imageToString(image);

        character.setDeleted(false);
        character.setImage(stringImage);
        
        return characterRepository.save(character);
    }

    @Override
    public Character update(CharacterDto dto, Long id, MultipartFile image) {
        Character character = checkAndSetAtributesForUpdate(dto, id, image);        
        
        return characterRepository.save(character);
    }

    @Override
    public void delete(Long id) {
        characterRepository.deleteById(id);
    }

    @Override
    public Character getById(Long id) {
       return characterRepository.findById(id).get();
    }

    @Override
    public List<Character> getAll() {
        return characterRepository.findAll();
    }

    @Override
    public List<Character> getAllByIds(List<Long> characterIds) {
        return characterRepository.findAllById(characterIds);
    }

    @Override
    public List<Character> getByName(String name) {
        return characterRepository.findByName(name);
    }

    @Override
    public List<Character> getByAge(Integer age) {
        return characterRepository.findByAge(age);
    }

    @Override
    public List<Character> getByMovieId(Long movieId) {
        return characterRepository.findByMoviesId(movieId);
    }

    @Override
    public List<Character> getByWeight(Double weight) {
        return characterRepository.findByWeight(weight);
    }


    @Override
    public void addMovies(Long characterId, List<Long> moviesIds) {
        Character character = getById(characterId);
        movieRepository.findAllById(moviesIds)
                    .forEach(m -> character.getMovies().add(m));                 
    }

    @Override
    public void removeMovies(Long characterId, List<Long> moviesIds) {
        Character character = getById(characterId);
        character.getMovies()
                 .removeIf(m -> moviesIds.contains(m.getId()));
    }

    @Override
    public Character checkAndSetAtributesForUpdate(CharacterDto dto, Long id, MultipartFile image) {
        Character character = getById(id);

        if(!character.getName().equals(dto.getCharacterName()) && !dto.getCharacterName().isEmpty()){
            character.setName(dto.getCharacterName());
        }
        if(character.getAge() != dto.getCharacterAge() && dto.getCharacterAge() != null && dto.getCharacterAge() >= 0){
            character.setAge(dto.getCharacterAge());
        }
        if(!character.getHistory().equals(dto.getCharacterHistory()) && !dto.getCharacterHistory().isEmpty()){
            character.setHistory(dto.getCharacterHistory());
        }
        if(character.getWeight() != dto.getCharacterWeight() && dto.getCharacterWeight() != null && dto.getCharacterWeight() >= 0){
            character.setWeight(dto.getCharacterWeight());
        }
        if(!image.getOriginalFilename().isEmpty()){
            String imageString = imageService.imageToString(image);
            character.setImage(imageString);
        }
        return character;
    }
    
    public List<CharacterDetailedDto> getAllCharactersDetailed(){
        List<Character> characters = getAll();
        
        List<CharacterDetailedDto> charactersDetailed = mapper.mapAll(characters, CharacterDetailedDto.class);

        for (int i = 0; i < charactersDetailed.size(); i++) {
            charactersDetailed.get(i).setCharacterMovies(
                mapper.mapAll(characters.get(i).getMovies(), MovieSimpleDto.class));
        }
        return charactersDetailed;
    }

}

