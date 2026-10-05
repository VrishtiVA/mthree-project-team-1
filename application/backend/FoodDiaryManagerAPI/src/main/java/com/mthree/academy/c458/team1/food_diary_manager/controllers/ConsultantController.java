package com.mthree.academy.c458.team1.food_diary_manager.controllers;

import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.services.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consultant")
public class ConsultantController {

    @Autowired
    private ConsultantService consultantService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getConsultant(@PathVariable("id") int id) {

        //Find user
        User user = consultantService.getConsultantById(id);

        //Ensure user exists as a client
        if (user != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<Error>(new Error("Consultant not found"), HttpStatus.NOT_FOUND);
        }
    }

}
