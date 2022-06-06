package com.uade.recipes.service.user;

import com.uade.recipes.exceptions.userExceptions.EmailExistsException;
import com.uade.recipes.exceptions.userExceptions.RegistrationProcessIncompleteException;
import com.uade.recipes.exceptions.userExceptions.UserNameExistsException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.UserRepository;
import com.uade.recipes.service.email.EmailSenderImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementationTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailSenderImplementation emailSenderImplementation;
    @InjectMocks
    private UserServiceImplementation userServiceImplementation;

    private User testUser;
    private List<User> userTestList;

    @BeforeEach
    void setUp() {
        userTestList = new ArrayList<>();
        testUser = new User();
        testUser.setId(1);
        testUser.setUserName("agussturba");
        testUser.setEnabled(true);
        testUser.setAvatar(null);
        testUser.setName("Agustin");
        testUser.setEmail("agus@mail.com");
        testUser.setPassword("12345");
        userTestList.add(testUser);
    }

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(userTestList);
        List<User> userList = userServiceImplementation.getAllUsers();
        assertNotNull(userList);
        assertEquals(1, userList.size());
        verify(userRepository).findAll();
    }

    @Test
    void getUserByEmail() throws UserNotFoundException {
        when(userRepository.findByEmail(any(String.class))).thenReturn(java.util.Optional.ofNullable(testUser));
        User user = userServiceImplementation.getUserByEmail("agus@mail.com");
        assertNotNull(user);
        assertEquals("agussturba", user.getUserName());
        assertEquals("agus@mail.com", user.getEmail());
        verify(userRepository).findByEmail("agus@mail.com");
    }

    @Test
    void getUserByEmailAndAlias() throws UserNotFoundException {
        when(userRepository.findByEmailAndUserName(any(String.class), any(String.class))).thenReturn(java.util.Optional.ofNullable(testUser));
        User user = userServiceImplementation.getUserByEmailAndAlias("agus@mail.com", "agussturba");
        assertNotNull(user);
        assertEquals("agussturba", user.getUserName());
        assertEquals("agus@mail.com", user.getEmail());
        verify(userRepository).findByEmailAndUserName("agus@mail.com", "agussturba");
    }

    @Test
    void getUserByEmailAndPassword() throws UserNotFoundException {
        when(userRepository.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(java.util.Optional.ofNullable(testUser));
        User user = userServiceImplementation.getUserByEmailAndPassword("agus@mail.com", "12345");
        assertNotNull(user);
        assertEquals("agussturba", user.getUserName());
        assertEquals("agus@mail.com", user.getEmail());
        verify(userRepository).findByEmailAndPassword("agus@mail.com", "12345");
    }

    @Test
    void validateUserRegistration() throws UserNotFoundException {
        when(userRepository.findByEmailAndRegistrationTimestampGreaterThanAndRegistrationTimestampLessThanEqual(any(String.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(java.util.Optional.ofNullable(testUser));
        User user = userServiceImplementation.validateUserRegistration("agus@mail.com");
        assertNotNull(user);
        assertEquals("agussturba", user.getUserName());
        assertEquals("agus@mail.com", user.getEmail());
    }

    @Test
    void confirmEmail() throws UserNotFoundException {
        when(userRepository.findByEmailAndRegistrationTimestampGreaterThanAndRegistrationTimestampLessThanEqual(any(String.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(java.util.Optional.ofNullable(testUser));
        userServiceImplementation.confirmEmail("agus@mail.com");
        assertTrue(testUser.isEnabled());
        verify(userRepository).save(testUser);

    }

    @Test
    void getUserByAlias() throws UserNotFoundException {
        when(userRepository.findByUserName(any(String.class))).thenReturn(java.util.Optional.ofNullable(testUser));
        User user = userServiceImplementation.getUserByAlias("agussturba");
        assertNotNull(user);
        assertEquals("agussturba", user.getUserName());
        assertEquals("agus@mail.com", user.getEmail());
        verify(userRepository).findByUserName("agussturba");
    }

    @Test
    void saveOrUpdateUser() throws UserNameExistsException, EmailExistsException, UserNotFoundException {
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        //User user = userServiceImplementation.saveOrUpdateUser(testUser.toVO(), "GUEST");
        //assertNotNull(user);
        //assertEquals("agussturba", user.getUserName());
        //assertEquals("agus@mail.com", user.getEmail());
        //verify(userRepository).save(testUser);
    }

    @Test
    void changePassword() throws UserNotFoundException {
        when(userRepository.findByEmail(any(String.class))).thenReturn(java.util.Optional.ofNullable(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        userServiceImplementation.changePassword("agus@mail.com", "12346");
        assertEquals("12346", testUser.getPassword());
        verify(userRepository).save(testUser);
    }

    @Test
    void getUserById() throws UserNotFoundException {
        when(userRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(testUser));
        User user = userServiceImplementation.getUserById(1);
        assertNotNull(user);
        assertEquals("agussturba", user.getUserName());
        assertEquals("agus@mail.com", user.getEmail());
        verify(userRepository).findById(1);
    }

    @Test
    void isRegistryComplete() throws UserNotFoundException, RegistrationProcessIncompleteException {
        when(userRepository.findByEmail(any(String.class))).thenReturn(java.util.Optional.ofNullable(testUser));
        userServiceImplementation.isRegistryComplete("agus@mail.com");
        verify(userRepository).findByEmail("agus@mail.com");
    }
}