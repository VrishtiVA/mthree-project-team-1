package com.mthree.academy.c458.team1.food_diary_manager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    /**
     * JsonIgnore: Password should not be included in responses
     */
    @JsonIgnore
    @Column(name = "password")
    private String password;

    /**
     * Orphan removal: indicating unowned diaries should be disposed of if a client is deleted.
     * Optional: A user may even not have a diary e.g. if they are a consultant.
     * JsonIgnore: Not to be included in JSON responses.
     */
    @JsonIgnore
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Diary diary;

    /**
     * Orphan removal: indicating unowned goals should be disposed of if a client is deleted.
     * JsonIgnore: Not to be included in JSON responses.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> goals = new ArrayList<>();

    /**
     * JsonIgnore: Not to be included in JSON responses.
     */
    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(userName, user.userName) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && role == user.role && Objects.equals(password, user.password) && Objects.equals(diary, user.diary) && Objects.equals(goals, user.goals) && Objects.equals(clients, user.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, firstName, lastName, role, password, diary, goals, clients);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", diary=" + diary +
                ", goals=" + goals +
                ", clients=" + clients +
                '}';
    }
}
