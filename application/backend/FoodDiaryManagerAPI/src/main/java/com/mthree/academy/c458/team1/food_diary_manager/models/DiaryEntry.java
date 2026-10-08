package com.mthree.academy.c458.team1.food_diary_manager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class DiaryEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "diary_entry_id", nullable = false)
	private int diaryEntryId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "diary_day_id", nullable = false)
	@JsonIgnore
	private DiaryDay day;

	@ManyToOne(optional = false)
	@JoinColumn(name = "food_id", nullable = false)
	@JsonIgnore
	private Food food;

	@JsonProperty("name")
	public String getFoodName() {
		return food != null ? food.getName() : null;
	}


	private double calories;
	private double protein;
	private double fat;
	private double carbohydrates;
	private double sugars;
	private double fibre;
	private double salt;

	@Column(name = "amount", nullable = true)
	private Integer amount;

	private LocalTime time;

	public DiaryEntry() {
	}

	public int getDiaryEntryId() {
		return diaryEntryId;
	}

	public DiaryDay getDay() {
		return day;
	}

	public void setDay(DiaryDay day) {
		this.day = day;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}
}