package com.mthree.academy.c458.team1.food_diary_manager.models;

import java.time.LocalDate;
import java.util.List;

public class GoalPerformance {
    private LocalDate date;
    private List<GoalTracking> goals;
    private double overallScore;

    public GoalPerformance(LocalDate date, List<GoalTracking> goals, double overallScore) {
        this.date = date;
        this.goals = goals;
        this.overallScore = overallScore;
    }
    public LocalDate getDate() {
        return date;
    }

    public List<GoalTracking> getGoals() {
        return goals;
    }
    public double getOverallScore() {
        return overallScore;
    }
}
