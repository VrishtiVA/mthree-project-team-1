package com.mthree.academy.c458.team1.food_diary_manager.services;


import com.fasterxml.jackson.core.JsonParseException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.APIException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.FoodNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.models.Diary;
import com.mthree.academy.c458.team1.food_diary_manager.models.Food;

import java.time.LocalDate;
import java.time.LocalTime;

public interface FoodApiService {


	Food searchFoodByBarcode(String foodBarcode) throws APIException;

	String getBarcodeUsingName(String foodName) throws FoodNotFoundException, APIException;

	Diary calculateFields(Food food, int amount, LocalTime time, LocalDate date);

}
