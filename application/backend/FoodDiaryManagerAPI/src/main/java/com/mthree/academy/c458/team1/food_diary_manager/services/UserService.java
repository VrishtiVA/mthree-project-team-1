package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignInRequest;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignUpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

public interface UserService {

    public SignInRequest validateSignInRequest(SignInRequest signInRequest) throws IllegalArgumentException;

    public SignUpRequest validateSignUpRequest(SignUpRequest signUpRequest) throws IllegalArgumentException;

    public User createUser(SignUpRequest signUpRequest);

    public Authentication authenticateUser(String username, String password) throws BadCredentialsException;

    public User getUserByUserName(String username);

    public User getUserById(int userId);
}
