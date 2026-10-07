package com.mthree.academy.c458.team1.food_diary_manager.models.users;

import java.util.Objects;

public record SignUpRequest(
        String userName,
        String password,
        String role,
        String firstName,
        String lastName
) {
    /* IDE Generated equals and hashcode overrides */

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SignUpRequest that = (SignUpRequest) o;
        return Objects.equals(role, that.role) && Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && Objects.equals(lastName, that.lastName) && Objects.equals(firstName, that.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, role, firstName, lastName);
    }
}