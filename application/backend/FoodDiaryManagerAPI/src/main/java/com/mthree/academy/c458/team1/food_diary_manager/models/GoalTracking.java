package com.mthree.academy.c458.team1.food_diary_manager.models;

public class GoalTracking {
    private Goal goal;
    private double actualValue;
    private boolean met;

    public GoalTracking(Goal goal, double actualValue, boolean met) {
        this.goal = goal;
        this.actualValue = actualValue;
        this.met = met;
    }
    public Goal getGoal() {
        return goal;
    }

    public double getActualValue() {
        return actualValue;
    }

    public boolean isMet() {
        return met;
    }
}
