package com.works.archisproject.controller;

import com.works.archisproject.dto.request.AuthRequest;
import com.works.archisproject.dto.request.CreateUserRequest;
import com.works.archisproject.entity.Skill;
import com.works.archisproject.entity.User;
import com.works.archisproject.service.impl.UserService;
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
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Create a User Rest API",
            description = "Create a user Rest API is used to add a new user to database.")
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping("/addNewUser")
    public ResponseEntity<User> addUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/generateToken")
    @Operation(
            summary = "Create a Token Rest API",
            description = "Create a token Rest API is used to for user login.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The Tutorial with given Id was not found.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest request) {
        String generateToken = userService.generateToken(request);
        return new ResponseEntity<>(generateToken,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @Operation(
            summary = "Get All Users Rest API",
            description = "Get all users Rest API is used to get all users to database.")
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> getAllUsers = userService.getAllUsers();
        return new ResponseEntity<>(getAllUsers,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete User By Id Rest API",
            description = "Delete user by id Rest API is used to delete user to database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The user cannot list element.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        String deleteUserById = userService.deleteUserById(id);
        return new ResponseEntity<>(deleteUserById,HttpStatus.OK);
    }

}
