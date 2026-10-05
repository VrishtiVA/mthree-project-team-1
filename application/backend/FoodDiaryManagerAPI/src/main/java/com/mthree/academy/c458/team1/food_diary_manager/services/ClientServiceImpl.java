package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private UserService userService;

    @Override
    public User getClientById(int clientId) {
        User user = userService.getUserById(clientId);
        return (user != null && user.getRole() == UserRole.CLIENT) ? user : null;
    }

}
