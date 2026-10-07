package com.mthree.academy.c458.team1.food_diary_manager.models;

import javax.persistence.*;

@Entity
@Table(name = "food")
public class Food {

    public Food(String name,
                Double salt,
                Double calories,
                Double protein,
                Double fat,
                Double carbohydrates,
                Double sugars,
                Double fibre,
                String barcode) {

        this.name = name;
        this.salt = salt;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.sugars = sugars;
        this.fibre = fibre;
        this.barcode = barcode;
    }

    public Food() {}

    @Id
    @Column(name = "food_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int foodId;

    @Column(name = "food_name", nullable = false)
    private String name;

    @Column(name = "food_barcode", nullable = false)
    private String barcode;

    // Nutritional information per 100g

    @Column(name = "salt")
    private Double salt;

    @Column(name = "calories")
    private Double calories;

    @Column(name = "protein")
    private Double protein;

    @Column(name = "fat")
    private Double fat;

    @Column(name = "carbohydrates")
    private Double carbohydrates;

    @Column(name = "sugars")
    private Double sugars;

    @Column(name = "fibre")
    private Double fibre;


    // Getters and setters

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getSalt() {
        return salt;
    }

    public void setSalt(Double salt) {
        this.salt = salt;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Double getSugars() {
        return sugars;
    }

    public void setSugars(Double sugars) {
        this.sugars = sugars;
    }

    public Double getFibre() {
        return fibre;
    }

    public void setFibre(Double fibre) {
        this.fibre = fibre;
    }


    @Override
    public String toString() {
        return "Food{" +
                "foodId=" + foodId +
                ", name='" + name + '\'' +
                ", salt=" + salt +
                ", calories=" + calories +
                ", protein=" + protein +
                ", fat=" + fat +
                ", carbohydrates=" + carbohydrates +
                ", sugars=" + sugars +
                ", fibre=" + fibre +
                ", barcode='" + barcode + '\'' +
                '}';
    }
}
