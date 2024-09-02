package com.works.archisproject.controller;

import com.works.archisproject.dto.ApplicationDto;
import com.works.archisproject.entity.Application;
import com.works.archisproject.service.impl.ApplicationServiceImpl;
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
@RequestMapping("/api/v1/application")
public class ApplicationController {

    private final ApplicationServiceImpl applicationService;

    public ApplicationController(ApplicationServiceImpl applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @Operation(
            summary = "Create a Application Rest API",
            description = "Create a application Rest API is used to add a new application to database.")
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    public ResponseEntity<ApplicationDto> save(@Valid @RequestBody ApplicationDto applicationDto) {
        ApplicationDto save = applicationService.save(applicationDto);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    @Operation(
            summary = "Get All Applications Rest API",
            description = "Get all applications Rest API is used to get all applications to database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = Application.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The application cannot list element.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<List<ApplicationDto>> getAllApplication() {
        List<ApplicationDto> getAllApplication = applicationService.getAllApplication();
        return new ResponseEntity<>(getAllApplication,HttpStatus.OK);
    }

    // localhost:8080/api/v1/application/2
    @GetMapping("{id}")
    @Operation(
            summary = "Get Application By Id Rest API",
            description = "Get application by id Rest API is used to get application to database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = Application.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The application cannot list element.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable Long id) {
        ApplicationDto getApplicationById = applicationService.getApplicationById(id);
        return new ResponseEntity<>(getApplicationById,HttpStatus.OK);
    }

    @GetMapping("/getName/{name}")
    @Operation(
            summary = "Get Application By Name Rest API",
            description = "Get application by name Rest API is used to get application to database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = Application.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The application cannot list element.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApplicationDto> getApplicationByName(@PathVariable String name) {
        ApplicationDto getApplicationByName = applicationService.getApplicationByName(name);
        return new ResponseEntity<>(getApplicationByName,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Delete Application By Id Rest API",
            description = "Delete application by id Rest API is used to delete application to database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = Application.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The application cannot list element.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<String> deleteApplicationById(@PathVariable Long id) {
        String deleteApplicationById = applicationService.deleteApplicationById(id);
        return new ResponseEntity<>(deleteApplicationById,HttpStatus.OK);
    }


    @PutMapping("/update")
    @Operation(
            summary = "Update Application Rest API",
            description = "Update application Rest API is used to update application to database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = Application.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The application cannot list element.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApplicationDto> update(@Valid @RequestBody ApplicationDto applicationDto) {
        ApplicationDto update = applicationService.update(applicationDto);
        return new ResponseEntity<>(update,HttpStatus.OK);
    }

}
