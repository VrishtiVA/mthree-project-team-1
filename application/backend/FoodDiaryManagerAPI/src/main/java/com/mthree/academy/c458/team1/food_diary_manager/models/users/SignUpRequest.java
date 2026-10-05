package com.mthree.academy.c458.team1.food_diary_manager.models.users;

public record SignUpRequest(
        String userName,
        String password,
        String role,
        String firstName,
        String lastName
) {}