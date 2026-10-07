package com.mthree.academy.c458.team1.food_diary_manager.services;


import com.fasterxml.jackson.core.JsonParseException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.APIException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.FoodNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.models.Food;

public interface FoodApiService {


	Food searchFoodByBarcode(String foodBarcode);

	String getBarcodeUsingName(String foodName) throws FoodNotFoundException, APIException;

}
