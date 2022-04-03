package com.uade.recipes.controllers;


import com.uade.recipes.exceptions.userExceptions.InvalidEmailException;
import com.uade.recipes.exceptions.userExceptions.EmailExistsException;
import com.uade.recipes.exceptions.userExceptions.InvalidPasswordException;
import com.uade.recipes.exceptions.userExceptions.InvalidRoleException;
import com.uade.recipes.exceptions.userExceptions.UserNameExistsException;
import com.uade.recipes.model.User;
import com.uade.recipes.service.SequenceGeneratorService;
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
    private final SequenceGeneratorService sequenceGeneratorService;

    public UserController(UserService userService, SequenceGeneratorService sequenceGeneratorService) {
        this.userService = userService;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(email));
    }

    @GetMapping("/userName/{username}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserName(userName));
    }

    @PostMapping
    public ResponseEntity<User> saveUserClient(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveOrUpdateUser(userVo, "ROLE_CLIENT"));
    }

    @PostMapping("/admin")
    public ResponseEntity<User> saveUserStudent(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException {
        userVo.setIdUser(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveOrUpdateUser(userVo, "ROLE_ADMIN"));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveOrUpdateUser(userVo, userVo.getRole()));
    }
}
