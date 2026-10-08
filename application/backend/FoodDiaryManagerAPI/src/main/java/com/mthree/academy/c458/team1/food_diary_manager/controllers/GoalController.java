package com.mthree.academy.c458.team1.food_diary_manager.controllers;

import com.mthree.academy.c458.team1.food_diary_manager.models.Goal;
import com.mthree.academy.c458.team1.food_diary_manager.models.GoalPerformance;
import com.mthree.academy.c458.team1.food_diary_manager.models.GoalTracking;
import com.mthree.academy.c458.team1.food_diary_manager.services.GoalService;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.InvalidInputException;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.UserNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/api/client")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/{clientId}/goal")
    public ResponseEntity<?> createGoal(@PathVariable int clientId, @RequestBody Goal goal) {
        try {
            return new ResponseEntity<Goal>(goalService.createGoal(clientId, goal), HttpStatus.CREATED);

        } catch (UserNotFoundException | InvalidInputException ex) {
            return new ResponseEntity<Error>(
                new Error(ex.getMessage()),
                (ex instanceof InvalidInputException ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND)
            );
        } catch (Exception ex) {
            return new ResponseEntity<Error>(Error.SOMETHING_WENT_WRONG_MESSAGE(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{clientId}/goal/{goalId}")
    public ResponseEntity<?> updateGoal(@PathVariable int clientId, @PathVariable int goalId, @RequestBody Goal goal) {
        try {
            return new ResponseEntity<Goal>(goalService.updateGoal(clientId, goalId, goal), HttpStatus.OK);

        } catch (InvalidInputException | EntityNotFoundException | UserNotFoundException ex) {
            return new ResponseEntity<Error>(
                new Error(ex.getMessage()),
                (ex instanceof InvalidInputException ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND)
            );
        } catch (Exception ex) {
            return new ResponseEntity<Error>(Error.SOMETHING_WENT_WRONG_MESSAGE(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{clientId}/goal/{goalId}")
    public ResponseEntity<?> deleteGoal(@PathVariable int clientId, @PathVariable int goalId) {
        try {
            goalService.deleteGoal(clientId, goalId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<Error>(new Error(ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<Error>(Error.SOMETHING_WENT_WRONG_MESSAGE(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{clientId}/goal")
    public List<Goal> getGoals(@PathVariable int clientId,
                               @RequestParam(required = false) Boolean active) {
        return goalService.getGoals(clientId, active);
    }
    @GetMapping("/{clientId}/goal/tracking")
    public List<GoalTracking> getGoalsForDay(
            @PathVariable int clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return goalService.getGoalsForDay(clientId, date);
    }
    @GetMapping("/{clientId}/goal/performance")
    public List<GoalPerformance> getGoalPerformance(
            @PathVariable int clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return goalService.getGoalPerformance(clientId, startDate, endDate);
    }
}
