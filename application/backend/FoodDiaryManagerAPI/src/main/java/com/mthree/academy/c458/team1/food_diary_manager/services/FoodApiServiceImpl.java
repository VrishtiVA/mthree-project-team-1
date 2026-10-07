package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.mthree.academy.c458.team1.food_diary_manager.api.OpenFoodFactsAPI;
import com.mthree.academy.c458.team1.food_diary_manager.daos.FoodRepository;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.APIException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.FoodNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.models.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodApiServiceImpl implements FoodApiService {

	@Autowired
	OpenFoodFactsAPI openFoodFactsAPI;

	@Autowired
	FoodRepository foodRepository;


	public Food searchFoodByBarcode(String barcode) {

		Food food = foodRepository.findByBarcode(barcode);
		if (food == null) {
			try{
				food = openFoodFactsAPI.searchFoodByBarcode(barcode);

			} catch (FoodNotFoundException e) {
				System.out.println(e.getMessage());
				return null;
			}
			System.out.println("Saving to repository");
			foodRepository.save(food);
			return food;
		}

		System.out.println("Found in repository, no need to call API");
		return food;
	}


	public String getBarcodeUsingName(String name) throws FoodNotFoundException, APIException {

		Food food = foodRepository.findByName(name);

		if (food == null) {
			String str = openFoodFactsAPI.getBarcodeUsingName(name);
			System.out.println("Saving to repository");
			return str;
		}

		System.out.println("Found in repository, no need to call API");
		return null;
	}

}
