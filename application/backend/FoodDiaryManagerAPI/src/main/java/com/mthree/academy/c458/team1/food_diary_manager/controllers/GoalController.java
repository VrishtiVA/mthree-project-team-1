package com.mthree.academy.c458.team1.food_diary_manager.controllers;

import com.mthree.academy.c458.team1.food_diary_manager.models.Goal;
import com.mthree.academy.c458.team1.food_diary_manager.services.GoalService;
import org.springframework.web.bind.annotation.*;
import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.daos.UserRepository;

@RestController
@RequestMapping("/client")
public class GoalController {

    private final GoalService goalService;
    private final UserRepository userRepository;

    public GoalController(GoalService goalService, UserRepository userRepository) {
        this.goalService = goalService;
        this.userRepository = userRepository;
    }

    @PostMapping("/{clientId}/goal")
    public Goal createGoal(@PathVariable int clientId, @RequestBody Goal goal) {
        User client = userRepository.findById(clientId).orElseThrow();
        goal.setClient(client);

        return goalService.createGoal(goal);
    }

    @PutMapping("/{clientId}/goal/{goalId}")
    public Goal updateGoal(@PathVariable int clientId,
                           @PathVariable int goalId,
                           @RequestBody Goal goal) {

        User client = userRepository.findById(clientId).orElseThrow();
        goal.setGoalId(goalId);
        goal.setClient(client);

        return goalService.updateGoal(goal);
    }
    @DeleteMapping("/{clientId}/goal/{goalId}")
    public void deleteGoal(@PathVariable int clientId,
                           @PathVariable int goalId) {
        goalService.deleteGoal(goalId);
    }
}
