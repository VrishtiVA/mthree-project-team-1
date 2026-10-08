package com.mthree.academy.c458.team1.food_diary_manager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diary_day")
public class DiaryDay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "diary_day_id", nullable = false)
	private int diaryDayId;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@ManyToOne(optional = false)
	@JoinColumn(name = "diary_id", nullable = false)
	@JsonIgnore
	private Diary diary;

	@OneToMany(
			mappedBy = "day",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<DiaryEntry> entries = new ArrayList<>();

	public DiaryDay() {
	}

	public DiaryDay(LocalDate date) {
		this.date = date;
	}

	public int getDiaryDayId() {
		return diaryDayId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Diary getDiary() {
		return diary;
	}

	public void setDiary(Diary diary) {
		this.diary = diary;
	}

	public List<DiaryEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<DiaryEntry> entries) {
		this.entries = entries;
	}

	public void addEntry(DiaryEntry entry) {
		entries.add(entry);
		entry.setDay(this);
	}

	public void removeEntry(DiaryEntry entry) {
		entries.remove(entry);
		entry.setDay(null);
	}
}