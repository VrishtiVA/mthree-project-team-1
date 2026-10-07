package com.mthree.academy.c458.team1.food_diary_manager.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.APIException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.FoodNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.models.Food;

public interface FoodAPI {

	Food searchFoodByBarcode(String barcode) throws FoodNotFoundException, APIException;

	String getBarcodeUsingName(String barcode) throws FoodNotFoundException, JsonParseException, APIException;
}
