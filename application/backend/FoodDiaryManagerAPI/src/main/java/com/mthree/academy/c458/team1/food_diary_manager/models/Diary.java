package com.mthree.academy.c458.team1.food_diary_manager.models;

import javax.persistence.*;

@Entity
public class Diary {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "diary_id", nullable = false)
    private int diaryId;

    @OneToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    public Diary() {}

    public Diary(User client) {
        this.client = client;
    }

    /* ----- Getters ----- */
    public int getDiaryId() {return diaryId;}
    public User getClient() {return client;}

}
