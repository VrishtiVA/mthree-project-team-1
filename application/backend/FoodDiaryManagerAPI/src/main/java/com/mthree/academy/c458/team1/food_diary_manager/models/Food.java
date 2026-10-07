package com.mthree.academy.c458.team1.food_diary_manager.models;

import javax.persistence.*;

@Entity
@Table (name = "food")
public class Food {

    public Food(String name, Double salt, String barcode) {
        this.name = name;
        this.salt = salt;
        this.barcode = barcode;
    }

    public Food() {}


    @Id
    @Column(name = "food_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int foodId;

    @Column(name = "food_name", nullable = false)
    private String name;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "food_barcode", nullable = false)
    private String barcode;


    // Would have protein, calories, etc.
    @Column(name="salt")
    private Double salt;


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

    public Double getSalt() {
        return salt;
    }

    public void setSalt(Double salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodId=" + foodId +
                ", name='" + name + '\'' +
                ", salt=" + salt +
                ", barcode='" + barcode + '\'' +
                '}';
    }
}
