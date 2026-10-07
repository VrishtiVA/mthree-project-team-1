package com.mthree.academy.c458.team1.food_diary_manager.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class DiaryEntry {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "diary_entry_id", nullable = false)
	private int diaryEntryId;

	@ManyToOne(optional = false)
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

	public DiaryEntry() {
	}

	public DiaryEntry(int diaryEntryId, User client, double calories, double protein, double carbohydrates, double fat,
					  double fibre, double sugars, int amount, double salt, LocalDate date, LocalTime time, String name) {
		this.diaryEntryId = diaryEntryId;
		this.client = client;
		this.calories = calories;
		this.protein = protein;
		this.carbohydrates = carbohydrates;
		this.fat = fat;
		this.fibre = fibre;
		this.sugars = sugars;
		this.amount = amount;
		this.salt = salt;
		this.date = date;
		this.time = time;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;


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

	public int getDiaryEntryId() {
		return diaryEntryId;
	}

	public void setDiaryEntryId(int diaryEntryId) {
		this.diaryEntryId = diaryEntryId;
	}
}
