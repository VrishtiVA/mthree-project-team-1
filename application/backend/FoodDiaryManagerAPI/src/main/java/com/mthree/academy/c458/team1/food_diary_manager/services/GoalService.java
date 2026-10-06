package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.models.Goal;

public interface GoalService {
    Goal createGoal(Goal goal);

    Goal updateGoal(Goal goal);

    void deleteGoal(int goalId);
}
