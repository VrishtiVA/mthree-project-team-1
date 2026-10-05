package com.mthree.academy.c458.team1.food_diary_manager.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "password")
    private String password;

    /**
     * Orphan removal: indicating unowned diaries should be disposed of if a client is deleted.
     * Optional: A user may even not have a diary e.g. if they are a consultant.
     */
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Diary diary;

    /**
     * Orphan removal: indicating unowned goals should be disposed of if a client is deleted.
     */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> goals = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "consultant_id", nullable = true)
    private List<User> clients = new ArrayList<>();

    /* ----- Constructor ----- */
    public User() {}
    public User(UserRole role, String userName, String firstName, String lastName) {
        this.role = role;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /* ----- Getters ----- */
    public int getUserId() {return userId;}
    public String getUserName() {return userName;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public UserRole getRole() {return role;}
    public String getPassword() {return password;}
    public Diary getDiary() {return diary;}
    public List<Goal> getGoals() {return goals;}
    public List<User> getClients() {return clients;}

    /* ----- Setters ----- */
    public void setUserName(String userName) {this.userName = userName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setRole(UserRole role) {this.role = role;}
    public void setPassword(String password) {this.password = password;}
    public void setDiary(Diary diary) {this.diary = diary;}
    public void setGoals(List<Goal> goals) {this.goals = goals;}
    public void setClients(List<User> clients) {this.clients = clients;}

}
