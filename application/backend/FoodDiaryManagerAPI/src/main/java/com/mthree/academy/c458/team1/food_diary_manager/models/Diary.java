package com.mthree.academy.c458.team1.food_diary_manager.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id", nullable = false)
    private int diaryId;

    @OneToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @OneToMany(
            mappedBy = "diary",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DiaryDay> days = new ArrayList<>();

    public Diary() {
    }

    public Diary(User client) {
        this.client = client;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public User getClient() {
        return client;
    }

    public List<DiaryDay> getDays() {
        return days;
    }

    public void setDays(List<DiaryDay> days) {
        this.days = days;
    }

    public void addDay(DiaryDay day) {
        days.add(day);
        day.setDiary(this);
    }

    public void removeDay(DiaryDay day) {
        days.remove(day);
        day.setDiary(null);
    }
}