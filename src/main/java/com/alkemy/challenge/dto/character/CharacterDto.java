package com.alkemy.challenge.dto.character;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDto {
    
    private String characterName;
    private Integer characterAge;
    private Double characterWeight;
    private String characterHistory;
}
