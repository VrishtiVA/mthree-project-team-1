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

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/{clientId}/goal")
    public Goal createGoal(@PathVariable int clientId,
                           @RequestBody Goal goal) {
        return goalService.createGoal(clientId, goal);
    }

    @PutMapping("/{clientId}/goal/{goalId}")
    public Goal updateGoal(@PathVariable int clientId,
                           @PathVariable int goalId,
                           @RequestBody Goal goal) {
        return goalService.updateGoal(clientId, goalId, goal);
    }

    @DeleteMapping("/{clientId}/goal/{goalId}")
    public void deleteGoal(@PathVariable int clientId,
                           @PathVariable int goalId) {
        goalService.deleteGoal(clientId, goalId);
    }
}
