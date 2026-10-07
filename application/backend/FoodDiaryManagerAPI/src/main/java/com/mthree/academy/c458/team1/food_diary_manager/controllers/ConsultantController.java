package com.mthree.academy.c458.team1.food_diary_manager.controllers;

import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.services.ConsultantService;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/{consultantId}/client/{clientId}")
    public ResponseEntity<?> addClient(@PathVariable("consultantId") int consultantId, @PathVariable("clientId") int clientId) {
        try {
            //Add client
            consultantService.addClientToConsultant(clientId, consultantId);
            return new ResponseEntity<Void>(HttpStatus.CREATED);

        } catch (UserNotFoundException ex) {
            return new ResponseEntity<Error>(new Error(ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<Error>(Error.SOMETHING_WENT_WRONG_MESSAGE(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{consultantId}/clients")
    public ResponseEntity<?> getClients(@PathVariable("consultantId") int consultantId) {
        try {
            //Get clients
            List<User> clients = consultantService.getAllClients(consultantId);
            return new ResponseEntity<List<User>>(clients, HttpStatus.OK);

        } catch (UserNotFoundException ex) {
            return new ResponseEntity<Error>(new Error(ex.getMessage()), HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<Error>(Error.SOMETHING_WENT_WRONG_MESSAGE(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{consultantId}/client/{clientId}")
    public ResponseEntity<?> removeClient(@PathVariable("consultantId") int consultantId, @PathVariable("clientId") int clientId) {
        try {
            //Remove client
            consultantService.removeClientFromConsultant(clientId, consultantId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

        } catch (UserNotFoundException ex) {
            return new ResponseEntity<Error>(new Error(ex.getMessage()), HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<Error>(Error.SOMETHING_WENT_WRONG_MESSAGE(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
