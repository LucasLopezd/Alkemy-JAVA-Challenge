package com.alkemy.challenge.controllers;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alkemy.challenge.dto.gender.GenderCreatedDto;
import com.alkemy.challenge.dto.gender.GenderDto;
import com.alkemy.challenge.dto.gender.NewGenderDto;
import com.alkemy.challenge.mapper.GenericModelMapper;
import com.alkemy.challenge.models.Gender;
import com.alkemy.challenge.service.GenderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/genders")
@RequiredArgsConstructor
public class GenderController {

        private final GenderService genderService;
        private final GenericModelMapper mapper;

        @PostMapping("/new")
        public ResponseEntity<GenderCreatedDto> create(@RequestParam("name") String name, @RequestParam("image") MultipartFile image) {
                NewGenderDto dto = new NewGenderDto(name);
                Gender gender = genderService.create(dto, image);
                return ResponseEntity.ok((mapper.map(gender, GenderCreatedDto.class)));
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<GenderDto> delete(@PathVariable(value = "id") Long id) {
                genderService.delete(id);
                return new ResponseEntity<>(NO_CONTENT);
        }
}
