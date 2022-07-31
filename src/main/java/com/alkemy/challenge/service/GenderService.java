package com.alkemy.challenge.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alkemy.challenge.dto.gender.GenderUpdateDto;
import com.alkemy.challenge.dto.gender.NewGenderDto;
import com.alkemy.challenge.mapper.GenericModelMapper;
import com.alkemy.challenge.models.Gender;
import com.alkemy.challenge.respository.GenderRepository;
import com.alkemy.challenge.util.BasicCrud;

import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class GenderService implements Serializable, BasicCrud<Gender, GenderUpdateDto, NewGenderDto> {

    private final GenderRepository genderRepository;
    private final ImageService imageService;
    private final GenericModelMapper mapper;

    @Override
    public Gender create(NewGenderDto dto, MultipartFile image) {
        Gender gender = mapper.map(dto, Gender.class);
        String stringImage = imageService.imageToString(image);

        gender.setDeleted(false);
        gender.setImage(stringImage);
        return genderRepository.save(gender);
    }

    @Override
    public Gender update(GenderUpdateDto dto, Long id, MultipartFile image) {
        Gender gender = checkAndSetAtributesForUpdate(dto, id, image);
        
        return genderRepository.save(gender);
    }

    @Override
    public void delete(Long id) {
        genderRepository.deleteById(id);
    }

    @Override
    public Gender getById(Long id) {
        return genderRepository.findById(id).get();
    }

    @Override
    public List<Gender> getAll() {
        return genderRepository.findAll();
    }

    public List<Gender> getAllByIds(List<Long> genderIds) {
        return genderRepository.findAllById(genderIds);
    }

    @Override
    public Gender checkAndSetAtributesForUpdate(GenderUpdateDto dto, Long id, MultipartFile image) {
        Gender gender = getById(id);

        if(!gender.getName().equals(dto.getGenderName()) && !dto.getGenderName().isEmpty()){
            gender.setName(dto.getGenderName());
        }
        if(!image.getOriginalFilename().isEmpty()){
            String imageString = imageService.imageToString(image);
            gender.setImage(imageString);
        }
        return gender;
    }

}
