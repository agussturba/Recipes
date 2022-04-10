package com.uade.recipes.controllers;


import com.uade.recipes.exceptions.userExceptions.*;
import com.uade.recipes.model.User;
import com.uade.recipes.service.user.UserService;
import com.uade.recipes.vo.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(email));
    }

    @GetMapping("/userName/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserName(userName));
    }
    @GetMapping("/auth/")
    public ResponseEntity<User> getUserByUserNameAndPassword(@RequestParam String userName,@RequestParam String password) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserNameAndPassword(userName,password));
    }

    @PostMapping
    public ResponseEntity<User> saveUserClient(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveOrUpdateUser(userVo, "ROLE_CLIENT"));
    }

    @PostMapping("/admin")
    public ResponseEntity<User> saveUserStudent(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveOrUpdateUser(userVo, "ROLE_ADMIN"));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveOrUpdateUser(userVo, userVo.getRole()));
    }
}
