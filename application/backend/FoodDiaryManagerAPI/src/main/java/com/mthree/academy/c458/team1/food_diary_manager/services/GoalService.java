package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.models.Goal;

public interface GoalService {

    Goal createGoal(int clientId, Goal goal);

    Goal updateGoal(int clientId, int goalId, Goal goal);

    void deleteGoal(int clientId, int goalId);
}
