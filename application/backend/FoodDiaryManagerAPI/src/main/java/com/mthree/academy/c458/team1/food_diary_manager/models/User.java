package com.mthree.academy.c458.team1.food_diary_manager.models;

import javax.persistence.*;

@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column
    private String password;

    /* ----- Constructor ----- */
    public User() {}
    public User(UserRole role, int userId, String userName, String firstName, String lastName) {
        this.role = role;
        this.userId = userId;
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

    /* ----- Setters ----- */
    public void setUserName(String userName) {this.userName = userName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setRole(UserRole role) {this.role = role;}
    public void setPassword(String password) {this.password = password;}

}
