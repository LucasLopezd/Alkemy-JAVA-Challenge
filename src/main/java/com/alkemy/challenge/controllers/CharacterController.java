package com.alkemy.challenge.controllers;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;
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

import com.alkemy.challenge.dto.character.CharacterCreatedDto;
import com.alkemy.challenge.dto.character.CharacterDetailedDto;
import com.alkemy.challenge.dto.character.CharacterSimpleDto;
import com.alkemy.challenge.dto.character.CharacterDto;
import com.alkemy.challenge.mapper.GenericModelMapper;
import com.alkemy.challenge.models.Character;
import com.alkemy.challenge.service.CharacterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

        private final CharacterService characterService;
        private final GenericModelMapper mapper;

        @PostMapping("/new")
        public ResponseEntity<CharacterCreatedDto> create(@RequestParam("image") MultipartFile image, 
                                                          @RequestParam("name") String name,
                                                          @RequestParam("age") Integer age,
                                                          @RequestParam("weight") Double weight,
                                                          @RequestParam("history") String history) {
                
                CharacterDto dto = new CharacterDto(name, age, weight, history);               
                Character character = characterService.create(dto, image);
                return ResponseEntity.ok((mapper.map(character, CharacterCreatedDto.class)));
        }
        
        @PutMapping("/update")
        public ResponseEntity<CharacterDetailedDto> update(@RequestParam(value = "newImage", required = false) MultipartFile image, 
                                                           @RequestParam(value = "newName", required = false) String name,
                                                           @RequestParam(value = "newAge", required = false) Integer age,
                                                           @RequestParam(value = "newWeight", required = false) Double weight,
                                                           @RequestParam(value = "newHistory", required = false) String history,
                                                           @RequestParam("id") Long id){

                                                                
                CharacterDto dto = new CharacterDto(name, age, weight, history);                                      
                Character character = characterService.update(dto, id, image);
                return new ResponseEntity<>(mapper.map(character, CharacterDetailedDto.class), OK);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<CharacterDetailedDto> delete(@PathVariable(value = "id") Long id) {
                characterService.delete(id);
                return new ResponseEntity<>(NO_CONTENT);
        }

        @GetMapping("/{id}")
        public ResponseEntity<CharacterDetailedDto> getById(@PathVariable(value = "id") Long id) {
                Character character = characterService.getById(id);
                return new ResponseEntity<>(mapper.map(character, CharacterDetailedDto.class), OK);
        }
        
        @GetMapping("/all")
        public ResponseEntity<List<CharacterSimpleDto>> getAll() {
                return new ResponseEntity<>(mapper.mapAll(characterService.getAll(), CharacterSimpleDto.class), OK);
        }
        
        @GetMapping("/all/detailed")
        public ResponseEntity<List<CharacterDetailedDto>> getAllDetailedCharacters() {
                return new ResponseEntity<>(characterService.getAllCharactersDetailed(), OK);
        }

        @GetMapping(params = "name")
        public ResponseEntity<List<CharacterSimpleDto>> getByName(
                        @RequestParam(value = "name", required = false) String name) {
                return (name.isEmpty())
                                ? new ResponseEntity<>(
                                                mapper.mapAll(characterService.getAll(), CharacterSimpleDto.class), OK)
                                : new ResponseEntity<>(mapper.mapAll(characterService.getByName(name),
                                                CharacterSimpleDto.class), OK);
        }

        @GetMapping(params = "age")
        public ResponseEntity<List<CharacterSimpleDto>> getByAge(
                        @RequestParam(value = "age", required = false) Integer age) {
                return (age < 0 || age == null)
                                ? new ResponseEntity<>(
                                                mapper.mapAll(characterService.getAll(), CharacterSimpleDto.class), OK)
                                : new ResponseEntity<>(
                                                mapper.mapAll(characterService.getByAge(age), CharacterSimpleDto.class),
                                                OK);
        }

        @GetMapping(params = "weight")
        public ResponseEntity<List<CharacterSimpleDto>> getByAge(
                        @RequestParam(value = "weight", required = false) Double weight) {
                return (weight <= 0 || weight == null)
                        ? new ResponseEntity<>(mapper.mapAll(characterService.getAll(), CharacterSimpleDto.class), OK)
                        : new ResponseEntity<>(mapper.mapAll(characterService.getByWeight(weight),CharacterSimpleDto.class), OK);
        }

        @GetMapping(params = "movieid")
        public ResponseEntity<List<CharacterSimpleDto>> getByMovieId(
                        @RequestParam(value = "movieid", required = false) Long movieId) {
                                
                return (movieId < 1 || Objects.isNull(movieId))
                                ? new ResponseEntity<>(
                                                mapper.mapAll(characterService.getAll(), CharacterSimpleDto.class), OK)
                                : new ResponseEntity<>(mapper.mapAll(characterService.getByMovieId(movieId),
                                                CharacterSimpleDto.class), OK);
        }

}
