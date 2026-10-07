package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.models.Goal;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.InvalidInputException;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.UserNotFoundException;

import javax.persistence.EntityNotFoundException;

public interface GoalService {

    Goal createGoal(int clientId, Goal goal) throws UserNotFoundException, InvalidInputException;

    Goal updateGoal(int clientId, int goalId, Goal goal) throws UserNotFoundException, EntityNotFoundException, InvalidInputException;

    void deleteGoal(int clientId, int goalId) throws EntityNotFoundException;
}
