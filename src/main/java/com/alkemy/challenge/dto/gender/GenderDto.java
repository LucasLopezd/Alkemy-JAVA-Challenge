package com.alkemy.challenge.dto.gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenderDto {
    
    private Long genderId;
    private String genderName;
    private Boolean genderDeleted;
}
