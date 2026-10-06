package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.daos.DiaryRepository;
import com.mthree.academy.c458.team1.food_diary_manager.daos.UserRepository;
import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignInRequest;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignUpRequest;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityExistsException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Useful resources:
 * -
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    /* Mock dependencies of user service */
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private DiaryRepository diaryRepository;

    @InjectMocks
    private UserServiceImpl userService; //This is what we're testing

    private User user;

    private SignUpRequest getSignUpRequestSample() {
        return new SignUpRequest(
            "johnDoe_123",
            "password",
            "CLIENT",
            "John",
            "Doe"
        );
    }

    private SignInRequest getSignInRequestSample() {
        return new SignInRequest(
            "johnDoe_123",
            "password"
        );
    }

    /* ----- Behavior stubs ----- */

    private void whenFindByUsernameReturnNoUser() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());
    }

    private void whenFindByUsernameReturnUser() {
        user = new User();
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
    }

    private void whenPasswordEncoder() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    }

    private void whenSaveUserPassThrough() {
        when(userRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    private void whenSaveDiaryPassThrough() {
        when(diaryRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void validateSignUpRequestValid() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        whenFindByUsernameReturnNoUser();
        //Act
        SignUpRequest validatedUserRequest = userService.validateSignUpRequest(userRequest);
        //Assert
        assertEquals(userRequest, validatedUserRequest);
    }

    @Test
    void validateSignUpRequestUsernameAbsent() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
            null,
            userRequest.password(),
            userRequest.role(),
            userRequest.firstName(),
            userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ex) { return;
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }
    }

    @Test
    void validateSignUpRequestUsernameTooShort() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
            "hi",
            userRequest.password(),
            userRequest.role(),
            userRequest.firstName(),
            userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ignored) {
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }

        //Arrange
        userRequest = new SignUpRequest(
            "hi_",
            userRequest.password(),
            userRequest.role(),
            userRequest.firstName(),
            userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
        } catch (Exception ex) {
            fail("An exception should not have been thrown as input was in bounds.");
        }
    }

    @Test
    void validateSignUpRequestUsernameTooLong() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
            "hello".repeat(5) + "s",
            userRequest.password(),
            userRequest.role(),
            userRequest.firstName(),
            userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ex) { return;
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }

        //Arrange
        userRequest = new SignUpRequest(
            "hello".repeat(5),
            userRequest.password(),
            userRequest.role(),
            userRequest.firstName(),
            userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
        } catch (Exception ex) {
            fail("An exception should not have been thrown as input was in bounds.");
        }
    }

    @Test
    void validateSignUpRequestUsernameFormat() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
                "hello_world!",
                userRequest.password(),
                userRequest.role(),
                userRequest.firstName(),
                userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ex) { return;
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }
    }

    @Test
    void validateSignUpRequestDuplicateUsername() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        whenFindByUsernameReturnUser();
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Entity exists exception should have been thrown.");
        } catch (EntityExistsException ex) { return;
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }
    }

    @Test
    void validateSignUpRequestPasswordTooShort() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
            userRequest.userName(),
            "hello",
            userRequest.role(),
            userRequest.firstName(),
            userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ignored) {
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }

        //Arrange
        userRequest = new SignUpRequest(
            userRequest.userName(),
            "hello_",
            userRequest.role(),
            userRequest.firstName(),
            userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
        } catch (Exception ex) {
            fail("An exception should not have been thrown as input was in bounds.");
        }
    }

    @Test
    void validateSignUpRequestPasswordTooLong() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
            userRequest.userName(),
            "hellotherehoware".repeat(16) + "s",
            userRequest.role(),
            userRequest.firstName(),
            userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ignored) {
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }

        //Arrange
        userRequest = new SignUpRequest(
            userRequest.userName(),
            "hellotherehoware".repeat(16),
            userRequest.role(),
            userRequest.firstName(),
            userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
        } catch (Exception ex) {
            fail("An exception should not have been thrown as input was in bounds.");
        }
    }

    @Test
    void validateSignUpRequestPasswordFormat() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
            userRequest.userName(),
            "hi there".repeat(8),
            userRequest.role(),
            userRequest.firstName(),
            userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ex) { return;
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }
    }

    @Test
    void validateSignUpRequestFirstNameTooShort() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
                userRequest.userName(),
                userRequest.password(),
                userRequest.role(),
                "",
                userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ignored) {
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }

        //Arrange
        userRequest = new SignUpRequest(
                userRequest.userName(),
                userRequest.password(),
                userRequest.role(),
                "a",
                userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
        } catch (Exception ex) {
            fail("An exception should not have been thrown as input was in bounds.");
        }
    }

    @Test
    void validateSignUpRequestFirstNameTooLong() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
                userRequest.userName(),
                userRequest.password(),
                userRequest.role(),
                "hi_friends".repeat(10) + "s",
                userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ignored) {
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }

        //Arrange
        userRequest = new SignUpRequest(
                userRequest.userName(),
                userRequest.password(),
                userRequest.role(),
                "hi_friends".repeat(10),
                userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
        } catch (Exception ex) {
            fail("An exception should not have been thrown as input was in bounds.");
        }
    }

    @Test
    void validateSignUpRequestFirstNameFormat() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
                userRequest.userName(),
                userRequest.password(),
                userRequest.role(),
                "$john_the",
                userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ex) { return;
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }
    }

    @Test
    void validateSignUpRequestLastNameTooShort() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
                userRequest.userName(),
                userRequest.password(),
                userRequest.role(),
                userRequest.firstName(),
                ""
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ignored) {
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }

        //Arrange
        userRequest = new SignUpRequest(
                userRequest.userName(),
                userRequest.password(),
                userRequest.role(),
                userRequest.firstName(),
                "a"
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
        } catch (Exception ex) {
            fail("An exception should not have been thrown as input was in bounds.");
        }
    }

    @Test
    void validateSignUpRequestLastNameTooLong() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
            userRequest.userName(),
            userRequest.password(),
            userRequest.role(),
            userRequest.firstName(),
            "hi_friends".repeat(10) + "s"
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ignored) {
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }

        //Arrange
        userRequest = new SignUpRequest(
                userRequest.userName(),
                userRequest.password(),
                userRequest.role(),
                userRequest.firstName(),
                "hi_friends".repeat(10)
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
        } catch (Exception ex) {
            fail("An exception should not have been thrown as input was in bounds.");
        }
    }

    @Test
    void validateSignUpRequestLastNameFormat() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
                userRequest.userName(),
                userRequest.password(),
                userRequest.role(),
                userRequest.firstName(),
                "$the_doe"
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ex) { return;
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }
    }

    @Test
    void validateSignUpRequestUserRoleFormat() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        userRequest = new SignUpRequest(
                userRequest.userName(),
                userRequest.password(),
                "consultants",
                userRequest.firstName(),
                userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
            fail("Invalid input exception should have been thrown.");
        } catch (InvalidInputException ex) { return;
        } catch (Exception ex) {
            fail("The incorrect exception was thrown.");
        }

        //Arrange
        userRequest = new SignUpRequest(
                userRequest.userName(),
                userRequest.password(),
                "consultant",
                userRequest.firstName(),
                userRequest.lastName()
        );
        //Act & Assert
        try {
            userService.validateSignUpRequest(userRequest);
        } catch (Exception ex) {
            fail("An exception should not have been thrown.");
        }
    }

    @Test
    void validateSignInRequestValid() {

        //Arrange
        SignInRequest userRequest = getSignInRequestSample();
        //Act
        SignInRequest validatedUserRequest = userService.validateSignInRequest(userRequest);
        //Assert
        assertEquals(userRequest, validatedUserRequest);
    }

    @Test
    void validateSignInRequestMissingCredentials() {

        //Arrange
        SignInRequest userRequest = new SignInRequest("", "");
        //Act & Assert
        try {
            userService.validateSignInRequest(userRequest);
        } catch (InvalidInputException ex) { return;
        } catch (Exception ex) {
            fail("The incorrect exception was thrown");
        }
    }

    @Test
    void createUser() {

        //Arrange
        SignUpRequest userRequest = getSignUpRequestSample();
        whenPasswordEncoder();
        whenSaveUserPassThrough();
        whenSaveDiaryPassThrough();

        //Act
        User newUser = userService.createUser(userRequest);

        //Assert
        assertEquals(userRequest.userName(), newUser.getUserName());
        assertEquals("encodedPassword", newUser.getPassword());
        assertEquals(userRequest.role().toUpperCase(), newUser.getRole().toString());
        assertEquals(userRequest.firstName(), newUser.getFirstName());
        assertEquals(userRequest.lastName(), newUser.getLastName());
    }

    @Test
    void getUserByUserNamePresent() {

        //Arrange
        whenFindByUsernameReturnUser();
        //Act
        User user = userService.getUserByUserName("johnDoe_123");
        //Assert
        assertNotNull(user);
    }

    @Test
    void getUserByUserNameAbsent() {

        //Arrange
        whenFindByUsernameReturnNoUser();
        //Act
        User user = userService.getUserByUserName("johnDoe_123");
        //Assert
        assertNull(user);
    }

}