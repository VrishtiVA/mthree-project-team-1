package com.mthree.academy.c458.team1.food_diary_manager.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class DiaryEntryRequest {

	private String barcode;
	private int amount;
	private LocalTime time;
	private LocalDate date;

	public DiaryEntryRequest() {
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
}