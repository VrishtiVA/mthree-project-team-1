package com.mthree.academy.c458.team1.food_diary_manager.models.users;

import com.mthree.academy.c458.team1.food_diary_manager.models.User;

public record SignInResponse(
        User user,
        String jwt
) {}
