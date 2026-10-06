package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.daos.DiaryRepository;
import com.mthree.academy.c458.team1.food_diary_manager.daos.UserRepository;
import com.mthree.academy.c458.team1.food_diary_manager.models.Diary;
import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.models.UserRole;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignInRequest;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignUpRequest;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.InvalidInputException;
import com.mthree.academy.c458.team1.food_diary_manager.services.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityExistsException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public SignInRequest validateSignInRequest(SignInRequest signInRequest) throws InvalidInputException {

        String username = signInRequest.userName();
        String password = signInRequest.password();

        //Presence check
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new InvalidInputException("Required credentials are missed");
        }

        //Validate inputs
        username = validateUsernameFormat(username);
        password = validatePassword(password);

        //Return validated and sanitized inputs
        return new SignInRequest(username, password);
    }

    @Override
    public SignUpRequest validateSignUpRequest(SignUpRequest signUpRequest) throws InvalidInputException, EntityExistsException {

        String username = signUpRequest.userName();
        String password = signUpRequest.password();
        String firstName = signUpRequest.firstName();
        String lastName = signUpRequest.lastName();
        String role = signUpRequest.role();

        //Presence check
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new InvalidInputException("Required credentials are missed");
        }

        //Validate inputs
        username = validateUsernameFormat(username);
        password = validatePassword(password);
        firstName = validateFirstName(firstName);
        lastName = validateLastName(lastName);
        role = validateUserRole(role);

        if (!validateUsernameIsUnique(username)) {
            throw new EntityExistsException("The provided username has already been taken");
        }

        //Return validated and sanitized imports
        return new SignUpRequest(username, password, role, firstName, lastName);
    }

    /**
     * Method to sanitize and validate the username.
     * Rules: Alphanumerics, minimum 3 characters, maximum 25 characters, and limited special characters: _
     * @param username The username to validate
     * @return A valid username
     * @throws InvalidInputException if the username cannot be considered valid.
     */
    private String validateUsernameFormat(String username) throws InvalidInputException {

        username = username.trim().replace("\\s+", "");

        if (!username.matches("[a-zA-Z0-9_]{3,25}")) {
            throw new InvalidInputException(
                "The provided username is invalid. " +
                "The username should have 3-25 characters consisting of only alphanumerics and/or _"
            );
        }

        return username;
    }

    /**
     * Method to ensure username is unique
     * @param username The username to validate
     * @return true if username is unique, otherwise false.
     */
    public boolean validateUsernameIsUnique(String username) {
        return userRepository.findByUserName(username).isEmpty();
    }

    /**
     * Method to sanitize and validate the password.
     * Rules: Alphanumerics, 6-256 characters, permitted special characters: {@code _-£*()$%}, no spaces allowed.
     * @param password The password to validate
     * @return A valid password
     * @throws InvalidInputException if the password cannot be considered valid.
     */
    private String validatePassword(String password) throws InvalidInputException {

        password = password.trim();

        if (!password.matches("[a-zA-Z0-9_-£*()$%]{6,256}")) {
            throw new InvalidInputException(
                "The provided password is invalid. " +
                "The password should have 6-256 characters consisting of only alphanumerics and/or _-£*()$%. " +
                "No spaces are allowed."
            );
        }

        return password;
    }

    /**
     * Method to sanitize and validate the first name.
     * Rules: Alphanumerics, minimum 1 character, maximum 100 characters, and single spaces allowed.
     * @param name The first name to validate
     * @return A valid first name
     * @throws InvalidInputException if the name cannot be considered valid.
     */
    private String validateFirstName(String name) throws InvalidInputException {

        name = name.trim().replace("\\s+", " ");

        if (!name.matches("[a-zA-Z0-9_ ]{1,100}")) {
            throw new InvalidInputException(
                "The provided first name is invalid. " +
                "The first name should have 1-100 characters consisting of only alphanumerics and/or spaces."
            );
        }

        return name;
    }

    /**
     * Method to sanitize and validate the last name.
     * Rules: Alphanumerics, minimum 1 character, maximum 100 characters, and single spaces allowed.
     * @param name The last name to validate
     * @return A valid last name
     * @throws InvalidInputException if the name cannot be considered valid.
     */
    private String validateLastName(String name) throws InvalidInputException {

        name = name.trim().replace("\\s+", " ");

        if (!name.matches("[a-zA-Z0-9_ ]{1,100}")) {
            throw new InvalidInputException(
                "The provided last name is invalid. " +
                "The last name should have 1-100 characters consisting of only alphanumerics and/or spaces."
            );
        }

        return name;
    }

    /**
     * Method to validate user role.
     * @param role The role string to validate
     * @return A valid role string
     * @throws InvalidInputException if the role cannot be considered valid.
     */
    private String validateUserRole(String role) throws InvalidInputException {

        role = role.trim().replace("\\s+", "");

        try {
            role = UserRole.valueOf(role.toUpperCase()).toString();
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException("The provided user role is invalid.");
        }

        return role;
    }

    @Override
    @Transactional
    public User createUser(SignUpRequest signUpRequest) {

        //Create new user
        UserRole userRole = UserRole.valueOf(signUpRequest.role());
        User newUser = new User(
            userRole,
            signUpRequest.userName(),
            signUpRequest.firstName(),
            signUpRequest.lastName()
        );
        //Encode password - do not store as plain text
        newUser.setPassword(passwordEncoder.encode(signUpRequest.password()));

        //Save user
        newUser = userRepository.save(newUser);

        //Create a diary if the user is a client
        if (userRole == UserRole.CLIENT) {
            Diary clientDiary = new Diary(newUser);
            diaryRepository.save(clientDiary);
            newUser.setDiary(clientDiary);
            newUser = userRepository.save(newUser);
        }

        return newUser;
    }

    @Override
    public Authentication authenticateUser(String username, String password) throws BadCredentialsException {

        //Find user details
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        //Check password matches
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        //Return authentication object
        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByUserName(username).orElse(null);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

}
