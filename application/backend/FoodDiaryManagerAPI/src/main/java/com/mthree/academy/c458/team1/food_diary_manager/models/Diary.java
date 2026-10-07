package com.mthree.academy.c458.team1.food_diary_manager.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Diary {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "diary_id", nullable = false)
    private int diaryId;

    @OneToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    private double calories;
    private double protein;
    private double fat;
    private double carbohydrates;
    private double sugars;
    private double fibre;
    private double salt;
    private int amount;
    private LocalTime time;
    private LocalDate date;

    public Diary() {}

    public Diary(User client) {
        this.client = client;
    }

    /* ----- Getters ----- */
    public int getDiaryId() {return diaryId;}
    public User getClient() {return client;}

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getSugars() {
        return sugars;
    }

    public void setSugars(double sugars) {
        this.sugars = sugars;
    }

    public double getFibre() {
        return fibre;
    }

    public void setFibre(double fibre) {
        this.fibre = fibre;
    }

    public double getSalt() {
        return salt;
    }

    public void setSalt(double salt) {
        this.salt = salt;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
