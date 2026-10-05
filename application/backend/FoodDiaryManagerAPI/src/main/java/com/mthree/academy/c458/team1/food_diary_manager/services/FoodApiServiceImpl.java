package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.api.OpenFoodFactsAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodApiServiceImpl implements FoodApiService {

	@Autowired
	OpenFoodFactsAPI openFoodFactsAPI;

	public String searchFoodByName(String foodName) {
		return openFoodFactsAPI.searchFoodByName(foodName);
	}

	public String searchFoodByBarcode(String foodName) {

		return openFoodFactsAPI.searchFoodByBarcode(foodName);
	}

}
