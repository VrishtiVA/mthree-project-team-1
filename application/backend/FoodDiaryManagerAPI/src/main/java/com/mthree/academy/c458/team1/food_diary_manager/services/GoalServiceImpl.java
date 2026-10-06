package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.daos.GoalRepository;
import com.mthree.academy.c458.team1.food_diary_manager.models.Goal;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;

    public GoalServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    @Override
    public Goal updateGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    @Override
    public void deleteGoal(int goalId) {
        goalRepository.deleteById(goalId);
    }
}
