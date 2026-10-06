package com.mthree.academy.c458.team1.food_diary_manager.models.users;

import java.util.Objects;

public record SignInRequest(
        String userName,
        String password
) {
    /* IDE generated equals and hashcode overrides */

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SignInRequest request = (SignInRequest) o;
        return Objects.equals(userName, request.userName) && Objects.equals(password, request.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }
}
