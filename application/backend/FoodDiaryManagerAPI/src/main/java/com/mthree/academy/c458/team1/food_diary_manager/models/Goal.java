package com.mthree.academy.c458.team1.food_diary_manager.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table (name = "Goal")
public class Goal {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "goal_id", nullable = false)
    private int goalId;

    @Enumerated(EnumType.STRING)
    @Column(name="goal_subject", nullable = false)
    private GoalSubject goalSubject;

    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @Column(name = "min_target", nullable = true)
    private Double minTarget;

    @Column(name = "max_target", nullable = true)
    private Double maxTarget;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    public Goal() {}

    public Goal(GoalSubject goalSubject, User client) {
        this.goalSubject = goalSubject;
        this.client = client;
    }

    /* ----- Getters ----- */
    public int getGoalId() {return goalId;}
    public GoalSubject getGoalSubject() {return goalSubject;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public Double getMinTarget() {return minTarget;}
    public Double getMaxTarget() {return maxTarget;}
    public User getClient() {return client;}

    /* ----- Setters ----- */
    public void setGoalId(int goalId) {this.goalId = goalId;}
    public void setGoalSubject(GoalSubject goalSubject) {this.goalSubject = goalSubject;}
    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}
    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}
    public void setMinTarget(Double minTarget) {this.minTarget = minTarget;}
    public void setMaxTarget(Double maxTarget) {this.maxTarget = maxTarget;}
    public void setClient(User client) {this.client = client;}

}
