package com.uade.recipes.controllers;


import com.uade.recipes.exceptions.userExceptions.*;
import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import com.uade.recipes.model.Unit;
import com.uade.recipes.model.User;
import com.uade.recipes.service.user.UserService;
import com.uade.recipes.vo.UnitVo;
import com.uade.recipes.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "Get a list of users", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list of users"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),

    })
    public ResponseEntity<List<UserVo>> getAllUsers() {
        List<UserVo> result = transformListToVoList(userService.getAllUsers());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/email/{email}")
    @ApiOperation(value = "Get a user by his email", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved the user by his email"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "User not Found"),

    })
    public ResponseEntity<UserVo> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(email).toVO());
    }

    @GetMapping("/userName/{userName}")
    @ApiOperation(value = "Get a user by his userName", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved the user by his username"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "User not Found"),

    })
    public ResponseEntity<UserVo> getUserByUserName(@PathVariable String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserName(userName).toVO());
    }

    @GetMapping("/auth/")
    @ApiOperation(value = "Get a user by his userName and password", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved the user by his username and password"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "User not Found"),

    })
    public ResponseEntity<UserVo> getUserByUserNameAndPassword(@RequestParam String userName, @RequestParam String password) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserNameAndPassword(userName, password).toVO());
    }

    @PostMapping
    @ApiOperation(value = "Create a new user ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    public ResponseEntity<UserVo> saveUserClient(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveOrUpdateUser(userVo, "ROLE_CLIENT").toVO());
    }

    @PostMapping("/admin")
    @ApiOperation(value = "Create a new user admin", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new user admin"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    public ResponseEntity<UserVo> saveUserStudent(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveOrUpdateUser(userVo, "ROLE_ADMIN").toVO());
    }

    @PutMapping
    @ApiOperation(value = "Update a user", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated a user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    public ResponseEntity<UserVo> updateUser(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveOrUpdateUser(userVo, userVo.getRole()).toVO());
    }

    private List<UserVo> transformListToVoList(List<User> list){
        List<UserVo> result = new ArrayList<>();
        for(User obj: list){
            result.add(obj.toVO());
        }
        return result;
    }
}
