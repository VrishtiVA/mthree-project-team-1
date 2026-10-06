package com.mthree.academy.c458.team1.food_diary_manager.controllers;

import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignInRequest;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignInResponse;
import com.mthree.academy.c458.team1.food_diary_manager.models.users.SignUpRequest;
import com.mthree.academy.c458.team1.food_diary_manager.services.UserService;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.EntityAlreadyExistsException;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.InvalidInputException;
import com.mthree.academy.c458.team1.food_diary_manager.services.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        try {
            //Validate request
            signInRequest = userService.validateSignInRequest(signInRequest);

            //Authenticate and set security context
            Authentication authentication = userService.authenticateUser(signInRequest.userName(), signInRequest.password());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.getUserByUserName(signInRequest.userName());

            //Generate token
            String jwt = jwtService.generateJwt(authentication);

            //Serve response
            return new ResponseEntity<>(new SignInResponse(user, jwt), HttpStatus.OK);

        } catch (InvalidInputException ex) {
            return new ResponseEntity<Error>(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<Error>(new Error(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            //Log this error in practice (we're not sure what actually went wrong).
            //But we won't allow any crashes.
            return new ResponseEntity<Error>(Error.SOMETHING_WENT_WRONG_MESSAGE(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {

        try {
            //Validate
            signUpRequest = userService.validateSignUpRequest(signUpRequest);

            //Create User
            User user = userService.createUser(signUpRequest);

            //Serve Response
            return new ResponseEntity<User>(user, HttpStatus.CREATED);

        } catch (InvalidInputException | EntityAlreadyExistsException ex) {
            return new ResponseEntity<Error>(
                new Error(ex.getMessage()),
                (ex instanceof InvalidInputException ? HttpStatus.BAD_REQUEST : HttpStatus.CONFLICT)
            );

        } catch (Exception ex) {
            //Log this error in practice (we're not sure what actually went wrong).
            //But we won't allow any crashes.
            return new ResponseEntity<Error>(Error.SOMETHING_WENT_WRONG_MESSAGE(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
