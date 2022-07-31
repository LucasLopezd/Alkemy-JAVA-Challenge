package com.alkemy.challenge.dto.gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenderSimpleDto {
    
    private Long genderId;
    private String genderName;
    private String genderImage;
}
