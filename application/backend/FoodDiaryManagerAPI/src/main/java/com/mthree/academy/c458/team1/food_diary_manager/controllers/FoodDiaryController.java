package com.mthree.academy.c458.team1.food_diary_manager.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.APIException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.FoodNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.models.Food;
import com.mthree.academy.c458.team1.food_diary_manager.services.FoodApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/food")
public class FoodDiaryController {

	@Autowired
	private FoodApiService foodApiService;

	@GetMapping("/barcode/{barcode}")
	public Food addFoodByBarcode(@PathVariable("barcode") String barcode) {
		// Call foodDiaryService.findFoodByBarcode
		// That checks if the food already exists in the db
		// If it exists simply return that
		// else call the api method, then save food object to db
		//
		Food string = foodApiService.searchFoodByBarcode(barcode);
		if  (string != null) {
			System.out.println(string.toString());
		}
		return string;
	}

	@GetMapping("/name/{foodName}")
	public Food addFoodByName(@PathVariable("foodName") String foodName) {
		// Call foodDiaryService.findFoodByBarcode
		// That checks if the food already exists in the db
		// If it exists simply return that
		// else call the api method, then save food object to db
		//
		try {
			Food food;
			String barcode = foodApiService.getBarcodeUsingName(foodName);
			System.out.println(barcode);
			food = foodApiService.searchFoodByBarcode(barcode);
			return food;

		}
		catch (FoodNotFoundException | APIException e) {
			System.out.println(e.getMessage());
			return null; // TODO change this to return a ResponseEntity.
		}
	}

}
