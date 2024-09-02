package com.works.archisproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillDto {

    private Long id;

    @NotBlank(message = "Skill name cannot be blank.")
    private String name;

    @NotBlank(message = "Skill name cannot be blank.")
    private String description;

}
