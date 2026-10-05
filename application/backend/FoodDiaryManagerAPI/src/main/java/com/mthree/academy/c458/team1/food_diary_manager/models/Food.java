package com.mthree.academy.c458.team1.food_diary_manager.models;

import javax.persistence.*;

@Entity
public class Food {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "food_id", nullable = false)
    private int foodId;

    //...

}
