package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.models.Goal;
import com.mthree.academy.c458.team1.food_diary_manager.models.GoalPerformance;
import com.mthree.academy.c458.team1.food_diary_manager.models.GoalTracking;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.InvalidInputException;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.UserNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

public interface GoalService {


    Goal createGoal(int clientId, Goal goal) throws UserNotFoundException, InvalidInputException;

    Goal updateGoal(int clientId, int goalId, Goal goal) throws UserNotFoundException, EntityNotFoundException, InvalidInputException;

    void deleteGoal(int clientId, int goalId) throws EntityNotFoundException;

    List<Goal> getGoals(int clientId, Boolean active);

    List<GoalTracking> getGoalsForDay(int clientId, LocalDate date);

    List<GoalPerformance> getGoalPerformance(int clientId, LocalDate startDate, LocalDate endDate);
}
