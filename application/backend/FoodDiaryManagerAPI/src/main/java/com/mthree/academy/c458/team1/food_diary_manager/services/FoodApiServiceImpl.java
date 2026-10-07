package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.mthree.academy.c458.team1.food_diary_manager.api.OpenFoodFactsAPI;
import com.mthree.academy.c458.team1.food_diary_manager.daos.FoodRepository;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.APIException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.FoodNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.models.Diary;
import com.mthree.academy.c458.team1.food_diary_manager.models.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class FoodApiServiceImpl implements FoodApiService {

	@Autowired
	OpenFoodFactsAPI openFoodFactsAPI;

	@Autowired
	FoodRepository foodRepository;


	public Food searchFoodByBarcode(String barcode) throws APIException {

		Food food = foodRepository.findByBarcode(barcode);
		if (food == null) {
			try{
				food = openFoodFactsAPI.searchFoodByBarcode(barcode);

			} catch (FoodNotFoundException e) {
				System.out.println(e.getMessage());
				return null;
			}
			System.out.println("Saving Food to repository");
			foodRepository.save(food);
			return food;
		}

		System.out.println("Found food in repository using barcode, no need to call API");
		return food;
	}


	public String getBarcodeUsingName(String name) throws FoodNotFoundException, APIException {

		Food food = foodRepository.findByName(name);

		if (food == null) {
			String str = openFoodFactsAPI.getBarcodeUsingName(name);
			System.out.println("Saving Food to repository");
			return str;
		} else {
			System.out.println("Found food in repository using name, no need to call API");
			return food.getBarcode();
		}

	}

		public Diary calculateFields(Food food, int amount, LocalTime time, LocalDate date) {

			double multiplier = amount / 100.0;

			Diary diary = new Diary();

			diary.setAmount(amount);
			diary.setTime(time);
			diary.setDate(date);

			diary.setCalories(food.getCalories() * multiplier);
			diary.setProtein(food.getProtein() * multiplier);
			diary.setFat(food.getFat() * multiplier);
			diary.setCarbohydrates(food.getCarbohydrates() * multiplier);
			diary.setSugars(food.getSugars() * multiplier);
			diary.setFibre(food.getFibre() * multiplier);
			diary.setSalt(food.getSalt() * multiplier);
//			diary.setName(food.getName());

			return diary;
	}

}
