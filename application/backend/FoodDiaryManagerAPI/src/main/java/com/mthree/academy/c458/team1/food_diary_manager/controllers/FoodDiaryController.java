package com.mthree.academy.c458.team1.food_diary_manager.controllers;

import com.mthree.academy.c458.team1.food_diary_manager.services.FoodApiService;
import com.mthree.academy.c458.team1.food_diary_manager.services.FoodDiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/food")
public class FoodDiaryController {

	private FoodApiService foodDiaryService = new FoodApiService();

	@GetMapping("/{foodName}")
	public String addFoodBySearch(@PathVariable("foodName") String foodName) {
		String string = foodDiaryService.searchFoodByBarcode(foodName);
		System.out.println(string);
		return string;
	}

}
