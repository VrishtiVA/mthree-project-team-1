package com.mthree.academy.c458.team1.food_diary_manager.services;


import com.fasterxml.jackson.core.JsonParseException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.APIException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.FoodNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.models.Diary;
import com.mthree.academy.c458.team1.food_diary_manager.models.DiaryDay;
import com.mthree.academy.c458.team1.food_diary_manager.models.DiaryEntry;
import com.mthree.academy.c458.team1.food_diary_manager.models.Food;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface FoodApiService {


	Food searchFoodByBarcode(String foodBarcode) throws APIException, FoodNotFoundException;

	String getBarcodeUsingName(String foodName) throws FoodNotFoundException, APIException;

//	DiaryEntry calculateFields(Food food, int amount, LocalTime time, LocalDate date);

	DiaryEntry addDiaryEntry(String username, String barcode, int amount, LocalTime time, LocalDate date) throws APIException, FoodNotFoundException;
	DiaryEntry calculateFields(DiaryEntry diaryEntry, Food food);

	DiaryEntry getDiaryEntry(String username, int diaryEntryId);

	List<DiaryEntry> getDiaryEntries(String username);

	DiaryEntry updateDiaryEntry(
			String username,
			int diaryEntryId,
			String barcode,
			int amount,
			LocalTime time,
			LocalDate date
	) throws APIException, FoodNotFoundException;

	void deleteDiaryEntry(String username, int diaryEntryId);

	List<DiaryEntry> getDiaryEntriesForDay(
			String username,
			LocalDate date
	);



}
