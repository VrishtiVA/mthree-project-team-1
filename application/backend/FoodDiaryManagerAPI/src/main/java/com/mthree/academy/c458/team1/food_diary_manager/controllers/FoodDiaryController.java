package com.mthree.academy.c458.team1.food_diary_manager.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.APIException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.FoodNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.models.Diary;
import com.mthree.academy.c458.team1.food_diary_manager.models.DiaryEntryRequest;
import com.mthree.academy.c458.team1.food_diary_manager.models.Food;
import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.services.FoodApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping(value = "/api/food")
public class FoodDiaryController {

	@Autowired
	private FoodApiService foodApiService;

	@GetMapping("/barcode/{barcode}")
	public  ResponseEntity<?> addFoodByBarcode(@PathVariable("barcode") String barcode) {
		// Call foodDiaryService.findFoodByBarcode
		// That checks if the food already exists in the db
		// If it exists simply return that
		// else call the api method, then save food object to db
		//
		try {
			Food food = foodApiService.searchFoodByBarcode(barcode);
			if  (food != null) {
				System.out.println(food.toString());
			}
			return new ResponseEntity<Food>(food, HttpStatus.OK);

		} catch (APIException e) {
			return new ResponseEntity<Error>(new Error(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@GetMapping("/name/{foodName}")
	public ResponseEntity<?> addFoodByName(@PathVariable("foodName") String foodName) {
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
			return new ResponseEntity<Food>(food, HttpStatus.OK);

		}
		catch (FoodNotFoundException | APIException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Error>(new Error(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);


		}
	}


	@PostMapping("/diary")
	public  ResponseEntity<?> addDiaryEntry(@RequestBody DiaryEntryRequest request, Authentication authentication) {

		String user = authentication.getName();
		String barcode = request.getBarcode();
		int amount = request.getAmount();
		LocalTime time = request.getTime();
		LocalDate date = request.getDate();

		try {
			Food food = foodApiService.searchFoodByBarcode(barcode);
			if (food == null) {
				return new ResponseEntity<Error>(
						new Error("Food not found"),
						HttpStatus.NOT_FOUND
				);
			}
			Diary diary = foodApiService.calculateFields(
					food,
					amount,
					time,
					date
			);

			return new ResponseEntity<>(
					diary,
					HttpStatus.OK
			);

		} catch (APIException e) {
			return new ResponseEntity<Error>(new Error(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
