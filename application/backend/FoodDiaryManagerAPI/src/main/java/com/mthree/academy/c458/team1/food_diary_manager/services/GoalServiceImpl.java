package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.daos.GoalRepository;
import com.mthree.academy.c458.team1.food_diary_manager.models.*;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.InvalidInputException;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.UserNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.services.FoodApiService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final UserService userService;
    private final FoodApiService foodApiService;

    public GoalServiceImpl(GoalRepository goalRepository, UserService userService, FoodApiService foodApiService) {
        this.goalRepository = goalRepository;
        this.userService = userService;
        this.foodApiService = foodApiService;
    }
    private Goal validateGoal(Goal goal) throws InvalidInputException {

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

        return goal;
    }

    @Override
    public Goal createGoal(int clientId, Goal goal) throws UserNotFoundException, InvalidInputException {

        //Validate goal
        validateGoal(goal);

        //Validate client
        User client = userService.getUserById(clientId);

        if (client == null) {
            throw new InvalidInputException("Client not found");
        }

        if (client.getRole() != UserRole.CLIENT) {
            throw new InvalidInputException("User is not a client");
        }

        //Add goal
        goal.setClient(client);
        client.getGoals().add(goal);

        return goalRepository.save(goal);
    }

    @Override
    public Goal updateGoal(int clientId, int goalId, Goal goal) throws EntityNotFoundException, InvalidInputException {

        //Find goal
        Goal existingGoal = goalRepository.findById(goalId).orElse(null);

        //Validate goal
        if (existingGoal == null) {
            throw new EntityNotFoundException("Goal not found");
        }
        if (existingGoal.getClient().getUserId() != clientId) {
            throw new InvalidInputException("Goal does not belong to this client");
        }
        validateGoal(goal);

        //Update
        existingGoal.setGoalSubject(goal.getGoalSubject());
        existingGoal.setStartDate(goal.getStartDate());
        existingGoal.setEndDate(goal.getEndDate());
        existingGoal.setMinTarget(goal.getMinTarget());
        existingGoal.setMaxTarget(goal.getMaxTarget());

        return goalRepository.save(existingGoal);
    }

        @Override
        public void deleteGoal(int clientId, int goalId) throws EntityNotFoundException {

            Goal existingGoal = goalRepository.findById(goalId).orElse(null);

            if (existingGoal == null) {
                throw new EntityNotFoundException("Goal not found");
            }

            if (existingGoal.getClient().getUserId() != clientId) {
                throw new InvalidInputException("Goal does not belong to this client");
            }

            goalRepository.deleteById(goalId);
        }

    @Override
    public List<Goal> getGoals(int clientId, Boolean active) {

        User client = userService.getUserById(clientId);

        if (client == null) {
            throw new InvalidInputException("Client not found");
        }

        if (client.getRole() != UserRole.CLIENT) {
            throw new InvalidInputException("User is not a client");
        }

        List<Goal> goals = client.getGoals();

        if (active == null) {
            return goals;
        }

        LocalDate today = LocalDate.now();

        if (active) {
            return goals.stream()
                    .filter(goal -> goal.getEndDate() == null || !goal.getEndDate().isBefore(today))
                    .toList();
        }

        return goals.stream()
                .filter(goal -> goal.getEndDate() != null && goal.getEndDate().isBefore(today))
                .toList();
    }
    @Override
    public List<GoalTracking> getGoalsForDay(int clientId, LocalDate date) {

        User client = userService.getUserById(clientId);

        if (client == null) {
            throw new InvalidInputException("Client not found");
        }

        if (client.getRole() != UserRole.CLIENT) {
            throw new InvalidInputException("User is not a client");
        }

        List<DiaryEntry> entries =
                foodApiService.getDiaryEntriesForDay(client.getUserName(), date);

        List<GoalTracking> results = new ArrayList<>();

        for (Goal goal : client.getGoals()) {

            if (goal.getStartDate() != null
                    && date.isBefore(goal.getStartDate())) {
                continue;
            }

            if (goal.getEndDate() != null
                    && date.isAfter(goal.getEndDate())) {
                continue;
            }

            double actualValue = 0;

            for (DiaryEntry entry : entries) {
                actualValue += getNutrientValue(
                        entry,
                        goal.getGoalSubject()
                );
            }

            boolean met = true;

            if (goal.getMinTarget() != null
                    && actualValue < goal.getMinTarget()) {
                met = false;
            }

            if (goal.getMaxTarget() != null
                    && actualValue > goal.getMaxTarget()) {
                met = false;
            }

            results.add(
                    new GoalTracking(goal, actualValue, met)
            );
        }

        return results;
    }
    @Override
    public List<GoalPerformance> getGoalPerformance(
            int clientId,
            LocalDate startDate,
            LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            throw new InvalidInputException(
                    "End date cannot be before start date"
            );
        }

        List<GoalPerformance> performance = new ArrayList<>();

        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {

            List<GoalTracking> goals =
                    getGoalsForDay(clientId, currentDate);

            int goalsMet = 0;

            for (GoalTracking goal : goals) {
                if (goal.isMet()) {
                    goalsMet++;
                }
            }

            double overallScore = 0;

            if (!goals.isEmpty()) {
                overallScore = (double) goalsMet / goals.size() * 100;
            }

            performance.add(
                    new GoalPerformance(currentDate, goals, overallScore)
            );

            currentDate = currentDate.plusDays(1);
        }

        return performance;
    }
    private double getNutrientValue(DiaryEntry entry, GoalSubject subject) {

        switch (subject) {
            case CALORIES:
                return entry.getCalories();

            case PROTEIN:
                return entry.getProtein();

            case FAT:
                return entry.getFat();

            case CARBOHYDRATES:
                return entry.getCarbohydrates();

            case SUGARS:
                return entry.getSugars();

            case FIBER:
                return entry.getFibre();

            default:
                return 0;
        }
    }
}
