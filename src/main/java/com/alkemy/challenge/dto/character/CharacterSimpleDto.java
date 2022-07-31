package com.alkemy.challenge.dto.character;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterSimpleDto {
    
    private Long characterId;
    private String characterName;
    private String characterImage;
}
