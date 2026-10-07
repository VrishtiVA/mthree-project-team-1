package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.UserNotFoundException;

import java.util.List;

public interface ConsultantService {

    public User getConsultantById(int consultantId);

    public void addClientToConsultant(int clientId, int consultantId) throws UserNotFoundException;

    public List<User> getAllClients(int consultantId) throws UserNotFoundException;

    public void removeClientFromConsultant(int clientId, int consultantId) throws UserNotFoundException;

}
