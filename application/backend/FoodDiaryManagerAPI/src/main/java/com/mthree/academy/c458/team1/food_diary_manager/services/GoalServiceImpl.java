package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.daos.GoalRepository;
import com.mthree.academy.c458.team1.food_diary_manager.models.Goal;
import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.models.UserRole;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.InvalidInputException;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final UserService userService;

    public GoalServiceImpl(GoalRepository goalRepository, UserService userService) {
        this.goalRepository = goalRepository;
        this.userService = userService;
    }

    @Override
    public Goal createGoal(int clientId, Goal goal) {

        if (goal.getGoalSubject() == null) {
            throw new InvalidInputException("Goal subject is required");
        }
        if (goal.getMinTarget() != null && goal.getMinTarget() < 0) {
            throw new InvalidInputException("Minimum target cannot be negative");
        }

        if (goal.getMaxTarget() != null && goal.getMaxTarget() < 0) {
            throw new InvalidInputException("Maximum target cannot be negative");
        }

        if (goal.getMinTarget() != null && goal.getMaxTarget() != null
                && goal.getMinTarget() > goal.getMaxTarget()) {
            throw new InvalidInputException(
                    "Minimum target cannot be greater than maximum target"
            );
        }
        if (goal.getStartDate() != null && goal.getEndDate() != null
                && goal.getEndDate().isBefore(goal.getStartDate())) {
            throw new InvalidInputException(
                    "End date cannot be before start date"
            );
        }

        User client = userService.getUserById(clientId);

        if (client == null) {
            throw new InvalidInputException("Client not found");
        }

        if (client.getRole() != UserRole.CLIENT) {
            throw new InvalidInputException("User is not a client");
        }

        goal.setClient(client);
        client.getGoals().add(goal);

        return goalRepository.save(goal);
    }

    @Override
    public Goal updateGoal(int clientId, int goalId, Goal goal) {

        Goal existingGoal = goalRepository.findById(goalId).orElse(null);

        if (existingGoal == null) {
            throw new InvalidInputException("Goal not found");
        }
        if (existingGoal.getClient().getUserId() != clientId) {
            throw new InvalidInputException("Goal does not belong to this client");
        }
        if (goal.getMinTarget() != null && goal.getMinTarget() < 0) {
            throw new InvalidInputException("Minimum target cannot be negative");
        }

        if (goal.getMaxTarget() != null && goal.getMaxTarget() < 0) {
            throw new InvalidInputException("Maximum target cannot be negative");
        }

        if (goal.getGoalSubject() == null) {
            throw new InvalidInputException("Goal subject is required");
        }

        if (goal.getMinTarget() != null && goal.getMaxTarget() != null
                && goal.getMinTarget() > goal.getMaxTarget()) {
            throw new InvalidInputException(
                    "Minimum target cannot be greater than maximum target"
            );
        }
        if (goal.getStartDate() != null && goal.getEndDate() != null
                && goal.getEndDate().isBefore(goal.getStartDate())) {
            throw new InvalidInputException(
                    "End date cannot be before start date"
            );
        }

        existingGoal.setGoalSubject(goal.getGoalSubject());
        existingGoal.setStartDate(goal.getStartDate());
        existingGoal.setEndDate(goal.getEndDate());
        existingGoal.setMinTarget(goal.getMinTarget());
        existingGoal.setMaxTarget(goal.getMaxTarget());

        return goalRepository.save(existingGoal);
    }

        @Override
        public void deleteGoal(int clientId, int goalId) {

            Goal existingGoal = goalRepository.findById(goalId).orElse(null);

            if (existingGoal == null) {
                throw new InvalidInputException("Goal not found");
            }

            if (existingGoal.getClient().getUserId() != clientId) {
                throw new InvalidInputException("Goal does not belong to this client");
            }

            goalRepository.deleteById(goalId);
        }
}
