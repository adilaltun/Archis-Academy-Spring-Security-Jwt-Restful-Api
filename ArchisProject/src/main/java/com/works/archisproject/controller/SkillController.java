package com.works.archisproject.controller;

import com.works.archisproject.dto.SkillDto;
import com.works.archisproject.entity.Skill;
import com.works.archisproject.service.impl.SkillServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skill")
public class SkillController {

    private final SkillServiceImpl skillService;

    public SkillController(SkillServiceImpl skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    @Operation(
            summary = "Create a Skill Rest API",
            description = "Create a skill Rest API is used to add a new skill to database.")
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    public ResponseEntity<SkillDto> save(@Valid @RequestBody SkillDto skillDto) {
        SkillDto save = skillService.save(skillDto);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    @Operation(
            summary = "Get All Skills Rest API",
            description = "Get all skills Rest API is used to get all skills to database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = Skill.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The skill cannot list element.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<List<SkillDto>> getAllSkill() {
        List<SkillDto> allSkill = skillService.getAllSkill();
        return new ResponseEntity<>(allSkill,HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Get Skill By Id Rest API",
            description = "Get skill by id Rest API is used to get skill to database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = Skill.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The skill cannot list element.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<SkillDto> getSkillById(@PathVariable Long id) {
        SkillDto getSkillById = skillService.getSkillById(id);
        return new ResponseEntity<>(getSkillById,HttpStatus.OK);
    }

    @GetMapping("/getName/{name}")
    @Operation(
            summary = "Get Skill By Name Rest API",
            description = "Get skill by name Rest API is used to get skill to database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = Skill.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The skill cannot list element.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<SkillDto>  getSkillByName(@PathVariable String name) {
        SkillDto getSkillByName = skillService.getSkillByName(name);
        return new ResponseEntity<>(getSkillByName,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Delete Skill By Id Rest API",
            description = "Delete skill by id Rest API is used to delete skill to database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = Skill.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The skill cannot list element.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<String> deleteSkillById(@PathVariable Long id) {
        String deleteSkillById = skillService.deleteSkillById(id);
        return new ResponseEntity<>(deleteSkillById,HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(
            summary = "Update Skill Rest API",
            description = "Update skill Rest API is used to update skill to database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = Skill.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The skill cannot list element.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<SkillDto> update(@Valid @RequestBody SkillDto skillDto) {
        SkillDto update = skillService.update(skillDto);
        return new ResponseEntity<>(update,HttpStatus.OK);
    }

}
