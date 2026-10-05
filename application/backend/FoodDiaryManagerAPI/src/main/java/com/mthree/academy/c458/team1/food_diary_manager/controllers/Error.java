package com.mthree.academy.c458.team1.food_diary_manager.controllers;

import java.time.LocalDateTime;

/**
 * Error Message DTO.
 * Note: This object could be moved into a more appropriate package.
 */
public class Error {

    private LocalDateTime timestamp;
    private String message;

    public Error() {}

    public Error(String message) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }

    public LocalDateTime getTimestamp() {return timestamp;}
    public void setTimestamp(LocalDateTime timestamp) {this.timestamp = timestamp;}
    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}

    public static Error SOMETHING_WENT_WRONG_MESSAGE() {
        return new Error("Something went wrong");
    }
}
