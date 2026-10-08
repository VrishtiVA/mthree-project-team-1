package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.mthree.academy.c458.team1.food_diary_manager.api.OpenFoodFactsAPI;
import com.mthree.academy.c458.team1.food_diary_manager.daos.*;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.APIException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.FoodNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FoodApiServiceImpl implements FoodApiService {

	@Autowired
	OpenFoodFactsAPI openFoodFactsAPI;

	@Autowired
	FoodRepository foodRepository;

	@Autowired
	DiaryDayRepository diaryDayRepository;

	@Autowired
	DiaryRepository diaryRepository;

	@Autowired
	DiaryEntryRepository diaryEntryRepository;
	@Autowired
	UserRepository userRepository;


	public Food searchFoodByBarcode(String barcode) throws APIException, FoodNotFoundException {

		Food food = foodRepository.findByBarcode(barcode);

		if (food == null) {
			food = openFoodFactsAPI.searchFoodByBarcode(barcode);
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
	public DiaryEntry calculateFields(DiaryEntry diaryEntry, Food food) {

		double multiplier = diaryEntry.getAmount() / 100.0;

		diaryEntry.setCalories(food.getCalories() * multiplier);
		diaryEntry.setProtein(food.getProtein() * multiplier);
		diaryEntry.setFat(food.getFat() * multiplier);
		diaryEntry.setCarbohydrates(food.getCarbohydrates() * multiplier);
		diaryEntry.setSugars(food.getSugars() * multiplier);
		diaryEntry.setFibre(food.getFibre() * multiplier);
		diaryEntry.setSalt(food.getSalt() * multiplier);

		return diaryEntry;
	}
	public DiaryEntry addDiaryEntry(
			String username,
			String barcode,
			int amount,
			LocalTime time,
			LocalDate date)
			throws APIException, FoodNotFoundException {

		// Find user
		Optional<User> userOptional = userRepository.findByUserName(username);

		if (userOptional.isEmpty()) {
			throw new AccessDeniedException("User not found");
		}

		User user = userOptional.get();

		// Find user's diary
		Diary diary = diaryRepository.findByClient(user);

		if (diary == null) {
			diary = new Diary(user);
			diaryRepository.save(diary);

			//			throw new APIException("User does not have a diary");
		}

		// Find food
		Food food = searchFoodByBarcode(barcode);

		if (food == null) {
			throw new FoodNotFoundException(
					"Food not found for barcode: " + barcode
			);
		}

		// Find diary day for this date
		DiaryDay diaryDay =
				diaryDayRepository.findByDiaryAndDate(diary, date);

		// Create the day if it doesn't exist
		if (diaryDay == null) {

			diaryDay = new DiaryDay(date);

			diary.addDay(diaryDay);

			diaryDayRepository.save(diaryDay);
		}

		// Create diary entry
		DiaryEntry diaryEntry = new DiaryEntry();

		diaryEntry.setAmount(amount);
		diaryEntry.setTime(time);
		diaryEntry.setFood(food);

		// Calculate nutritional values
		calculateFields(diaryEntry, food);

		// Add entry to the day
		diaryDay.addEntry(diaryEntry);

		// Save entry
		diaryEntryRepository.save(diaryEntry);

		return diaryEntry;
	}
	public DiaryEntry getDiaryEntry(String username, int diaryEntryId) {

		User user = userRepository.findByUserName(username)
				.orElseThrow(() -> new AccessDeniedException("User not found"));

		DiaryEntry diaryEntry = diaryEntryRepository.findById(diaryEntryId)
				.orElseThrow(() -> new EntityNotFoundException("Diary entry not found"));

		if (!diaryEntry.getDay().getDiary().getClient().getUserName().equals(user.getUserName())) {
			throw new AccessDeniedException("You do not have access to this diary entry");
		}

		return diaryEntry;
	}
	public List<DiaryEntry> getDiaryEntries(String username) {

		User user = userRepository.findByUserName(username)
				.orElseThrow(() -> new AccessDeniedException("User not found"));

		Diary diary = diaryRepository.findByClient(user);

		if (diary == null) {
			return new ArrayList<>();
		}

		List<DiaryEntry> entries = new ArrayList<>();

		for (DiaryDay day : diary.getDays()) {
			entries.addAll(day.getEntries());
		}

		return entries;
	}

	public DiaryEntry updateDiaryEntry(
			String username,
			int diaryEntryId,
			String barcode,
			int amount,
			LocalTime time,
			LocalDate date)
			throws APIException, FoodNotFoundException {

		DiaryEntry diaryEntry = getDiaryEntry(username, diaryEntryId);

		Food food = searchFoodByBarcode(barcode);

		if (food == null) {
			throw new FoodNotFoundException(
					"Food not found for barcode: " + barcode
			);
		}

		Diary diary = diaryEntry.getDay().getDiary();

		DiaryDay oldDay = diaryEntry.getDay();

		// If the date has changed, move the entry to the new DiaryDay
		if (!oldDay.getDate().equals(date)) {

			DiaryDay newDay =
					diaryDayRepository.findByDiaryAndDate(diary, date);

			if (newDay == null) {
				newDay = new DiaryDay(date);
				diary.addDay(newDay);
				diaryDayRepository.save(newDay);
			}

			oldDay.removeEntry(diaryEntry);
			newDay.addEntry(diaryEntry);
		}

		diaryEntry.setFood(food);
		diaryEntry.setAmount(amount);
		diaryEntry.setTime(time);

		calculateFields(diaryEntry, food);

		return diaryEntryRepository.save(diaryEntry);
	}
	public void deleteDiaryEntry(String username, int diaryEntryId) {

		DiaryEntry diaryEntry = getDiaryEntry(username, diaryEntryId);

		DiaryDay day = diaryEntry.getDay();

		day.removeEntry(diaryEntry);

		diaryEntryRepository.delete(diaryEntry);
	}

	public List<DiaryEntry> getDiaryEntriesForDay(
			String username,
			LocalDate date) {

		User user = userRepository.findByUserName(username)
				.orElseThrow(() ->
						new AccessDeniedException("User not found"));

		Diary diary = diaryRepository.findByClient(user);

		if (diary == null) {
			return new ArrayList<>();
		}

		DiaryDay diaryDay =
				diaryDayRepository.findByDiaryAndDate(diary, date);

		if (diaryDay == null) {
			return new ArrayList<>();
		}

		return diaryDay.getEntries();
	}
}
