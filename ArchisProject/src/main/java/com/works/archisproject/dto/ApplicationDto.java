package com.works.archisproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {

    private Long id;

    @NotBlank(message = "Application name cannot be empty.")
    private String name;

    @NotBlank(message = "Application description cannot be empty.")
    private String description;


}
