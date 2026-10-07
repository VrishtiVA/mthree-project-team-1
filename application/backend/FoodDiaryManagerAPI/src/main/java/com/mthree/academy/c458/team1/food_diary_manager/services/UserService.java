package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignInRequest;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignUpRequest;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.InvalidInputException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import javax.persistence.EntityExistsException;

public interface UserService {

    public SignInRequest validateSignInRequest(SignInRequest signInRequest) throws InvalidInputException;

    public SignUpRequest validateSignUpRequest(SignUpRequest signUpRequest) throws InvalidInputException, EntityExistsException;

    public User createUser(SignUpRequest signUpRequest);

    public Authentication authenticateUser(String username, String password) throws BadCredentialsException;

    public User getUserByUserName(String username);

    public User getUserById(int userId);
}
