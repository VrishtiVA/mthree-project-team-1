package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.api.OpenFoodFactsAPI;

public class FoodApiService {

	OpenFoodFactsAPI openFoodFactsAPI = new OpenFoodFactsAPI();

	public String searchFoodByName(String foodName) {
		return openFoodFactsAPI.searchFoodByName(foodName);
	}

	public String searchFoodByBarcode(String foodName) {

		return openFoodFactsAPI.searchFoodByBarcode(foodName);
	}

}
