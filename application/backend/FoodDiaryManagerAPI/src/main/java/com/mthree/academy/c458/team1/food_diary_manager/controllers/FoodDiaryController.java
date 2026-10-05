package com.mthree.academy.c458.team1.food_diary_manager.controllers;

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

	@GetMapping("/{foodName}")
	public String addFoodBySearch(@PathVariable("foodName") String foodName) {
		String string = foodApiService.searchFoodByBarcode(foodName);
		System.out.println(string);
		return string;
	}

}
