package com.mthree.academy.c458.team1.food_diary_manager.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.APIException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.FoodNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.models.*;
import com.mthree.academy.c458.team1.food_diary_manager.services.FoodApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
//			if  (food != null) {
//				System.out.println(food.toString());
//			}
			return new ResponseEntity<Food>(food, HttpStatus.OK);

		} catch (APIException e) {
			return new ResponseEntity<Error>(new Error(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
		}
		catch (FoodNotFoundException e) {
			return new ResponseEntity<Error>(new Error(e.getMessage()), HttpStatus.NOT_FOUND);
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
			food = foodApiService.searchFoodByBarcode(barcode);
			return new ResponseEntity<Food>(food, HttpStatus.OK);

		}
		catch (FoodNotFoundException | APIException e) {
			return new ResponseEntity<Error>(new Error(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);


		}
	}


	@PostMapping("/diary")
	public  ResponseEntity<?> addDiaryEntry(@RequestBody DiaryEntryRequest request, Authentication authentication) {
		try {

		String username = authentication.getName();
		if (username == null) {
			throw new AccessDeniedException("No user found");
		}

			DiaryEntry diaryEntry = foodApiService.addDiaryEntry(
					username,
					request.getBarcode(),
					request.getAmount(),
					request.getTime(),
					request.getDate()
			);
			return new ResponseEntity<>(
					diaryEntry,
					HttpStatus.OK
			);

		} catch (FoodNotFoundException e) {

			return new ResponseEntity<>(
					new Error(e.getMessage()),
					HttpStatus.NOT_FOUND
			);

		} catch (APIException e) {

			return new ResponseEntity<>(
					new Error(e.getMessage()),
					HttpStatus.SERVICE_UNAVAILABLE
			);

		} catch (AccessDeniedException e) {

			return new ResponseEntity<>(
					new Error(e.getMessage()),
					HttpStatus.UNAUTHORIZED
			);
		}
	}

	@GetMapping("/diary/{id}")
	public ResponseEntity<?> getDiaryEntry(
			@PathVariable("id") int id,
			Authentication authentication) {

		try {

			String username = authentication.getName();

			DiaryEntry diaryEntry =
					foodApiService.getDiaryEntry(username, id);

			return new ResponseEntity<>(
					diaryEntry,
					HttpStatus.OK
			);

		} catch (EntityNotFoundException e) {

			return new ResponseEntity<>(
					new Error(e.getMessage()),
					HttpStatus.NOT_FOUND
			);

		} catch (AccessDeniedException e) {

			return new ResponseEntity<>(
					new Error(e.getMessage()),
					HttpStatus.UNAUTHORIZED
			);
		}
	}


	// READ ALL
	@GetMapping("/diary")
	public ResponseEntity<?> getDiaryEntries(
			Authentication authentication) {

		try {

			String username = authentication.getName();

			List<DiaryEntry> entries =
					foodApiService.getDiaryEntries(username);

			return new ResponseEntity<>(
					entries,
					HttpStatus.OK
			);

		} catch (AccessDeniedException e) {

			return new ResponseEntity<>(
					new Error(e.getMessage()),
					HttpStatus.UNAUTHORIZED
			);
		}
	}

	@PutMapping("/diary/{id}")
	public ResponseEntity<?> updateDiaryEntry(
			@PathVariable("id") int id,
			@RequestBody DiaryEntryRequest request,
			Authentication authentication) {

		try {

			String username = authentication.getName();

			DiaryEntry diaryEntry =
					foodApiService.updateDiaryEntry(
							username,
							id,
							request.getBarcode(),
							request.getAmount(),
							request.getTime(),
							request.getDate()
					);

			return new ResponseEntity<>(
					diaryEntry,
					HttpStatus.OK
			);

		} catch (FoodNotFoundException | EntityNotFoundException e) {

			return new ResponseEntity<>(
					new Error(e.getMessage()),
					HttpStatus.NOT_FOUND
			);

		} catch (APIException e) {

			return new ResponseEntity<>(
					new Error(e.getMessage()),
					HttpStatus.SERVICE_UNAVAILABLE
			);

		} catch (AccessDeniedException e) {

			return new ResponseEntity<>(
					new Error(e.getMessage()),
					HttpStatus.UNAUTHORIZED
			);
		}
	}

	@DeleteMapping("/diary/{id}")
	public ResponseEntity<?> deleteDiaryEntry(
			@PathVariable("id") int id,
			Authentication authentication) {

		try {

			String username = authentication.getName();

			foodApiService.deleteDiaryEntry(username, id);

			return new ResponseEntity<>(
					HttpStatus.NO_CONTENT
			);

		} catch (EntityNotFoundException e) {

			return new ResponseEntity<>(
					new Error(e.getMessage()),
					HttpStatus.NOT_FOUND
			);

		} catch (AccessDeniedException e) {

			return new ResponseEntity<>(
					new Error(e.getMessage()),
					HttpStatus.UNAUTHORIZED
			);
		}
	}

	@GetMapping("/diary/day/{date}")
	public ResponseEntity<?> getDiaryEntriesForDay(
			@PathVariable("date")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			Authentication authentication) {

		try {

			String username = authentication.getName();

			List<DiaryEntry> entries =
					foodApiService.getDiaryEntriesForDay(
							username,
							date
					);

			return new ResponseEntity<>(
					entries,
					HttpStatus.OK
			);

		} catch (AccessDeniedException e) {

			return new ResponseEntity<>(
					new Error(e.getMessage()),
					HttpStatus.UNAUTHORIZED
			);
		}
	}

}