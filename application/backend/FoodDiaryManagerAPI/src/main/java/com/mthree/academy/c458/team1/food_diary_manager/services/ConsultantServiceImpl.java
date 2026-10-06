package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.daos.UserRepository;
import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.models.UserRole;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultantServiceImpl implements ConsultantService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getConsultantById(int consultantId) {
        User user = userRepository.findById(consultantId).orElse(null);
        return (user != null && user.getRole() == UserRole.CONSULTANT) ? user : null;
    }

    private User getClientById(int clientId) {
        User user = userRepository.findById(clientId).orElse(null);
        return (user != null && user.getRole() == UserRole.CLIENT) ? user : null;
    }

    @Override
    public void addClientToConsultant(int clientId, int consultantId) throws UserNotFoundException {

        //Find relevant users
        User consultant = getConsultantById(consultantId);
        if (consultant == null) {
            throw new UserNotFoundException("Consultant not found");
        }
        User client = getClientById(clientId);
        if (client == null) {
            throw new UserNotFoundException("Client not found");
        }

        //Add if not already added - duplicates are naturally prevented from being added
        consultant.getClients().add(client);
        userRepository.save(consultant);
    }

    @Override
    public List<User> getAllClients(int consultantId) throws UserNotFoundException {

        //Find consultant
        User consultant = getConsultantById(consultantId);
        if (consultant == null) {
            throw new UserNotFoundException("Consultant not found");
        }

        //Get and return their clients
        return consultant.getClients();
    }

    @Override
    public void removeClientFromConsultant(int clientId, int consultantId) throws UserNotFoundException {

        //Find relevant users
        User consultant = getConsultantById(consultantId);
        if (consultant == null) {
            throw new UserNotFoundException("Consultant not found");
        }
        User client = getClientById(clientId);
        if (client == null) {
            throw new UserNotFoundException("Client not found");
        }

        //Remove if possible.
        boolean removed = consultant.getClients().remove(client);
        if (removed) userRepository.save(consultant);
    }

}
