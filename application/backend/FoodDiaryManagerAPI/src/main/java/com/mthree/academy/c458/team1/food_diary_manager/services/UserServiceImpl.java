package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.daos.DiaryRepository;
import com.mthree.academy.c458.team1.food_diary_manager.daos.UserRepository;
import com.mthree.academy.c458.team1.food_diary_manager.models.Diary;
import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.models.UserRole;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignInRequest;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignUpRequest;
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
    public SignInRequest validateSignInRequest(SignInRequest signInRequest) throws IllegalArgumentException {

        //Validate username
        //Validate password

        return signInRequest;
    }

    @Override
    public SignUpRequest validateSignUpRequest(SignUpRequest signUpRequest) throws IllegalArgumentException {

        //Validate role
        //Validate username
        //Validate first name
        //Validate last name
        //Validate password
        //Check username is unique too.

        return signUpRequest;
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
        return new UsernamePasswordAuthenticationToken(username, password);
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
