package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.api.OpenFoodFactsAPI;
import com.mthree.academy.c458.team1.food_diary_manager.daos.*;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.APIException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.FoodNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FoodApiServiceImplTest {

	@Mock
	private OpenFoodFactsAPI openFoodFactsAPI;

	@Mock
	private FoodRepository foodRepository;

	@Mock
	private DiaryDayRepository diaryDayRepository;

	@Mock
	private DiaryRepository diaryRepository;

	@Mock
	private DiaryEntryRepository diaryEntryRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private FoodApiServiceImpl foodApiService;

	private User user;
	private Diary diary;
	private DiaryDay diaryDay;
	private Food food;
	private DiaryEntry diaryEntry;

	@BeforeEach
	void setUp() {

		user = new User();
		user.setUserName("testuser");

		diary = new Diary(user);

		food = new Food();
		food.setName("Chicken Breast");
		food.setBarcode("123456789");
		food.setCalories(165.0);
		food.setProtein(31.0);
		food.setFat(3.6);
		food.setCarbohydrates(0.0);
		food.setSugars(0.0);
		food.setFibre(0.0);
		food.setSalt(0.1);

		diaryDay = new DiaryDay(LocalDate.of(2026, 10, 8));
		diary.addDay(diaryDay);

		diaryEntry = new DiaryEntry();
		diaryEntry.setFood(food);
		diaryEntry.setAmount(100);
		diaryEntry.setTime(LocalTime.of(12, 30));

		diaryDay.addEntry(diaryEntry);
	}

	@Test
	void calculateFields_shouldCalculateNutritionCorrectly() {

		DiaryEntry entry = new DiaryEntry();
		entry.setAmount(200);

		Food food = new Food();
		food.setCalories(165.0);
		food.setProtein(31.0);
		food.setFat(3.6);
		food.setCarbohydrates(0.0);
		food.setSugars(0.0);
		food.setFibre(0.0);
		food.setSalt(0.1);

		foodApiService.calculateFields(entry, food);

		assertEquals(330, entry.getCalories());
		assertEquals(62, entry.getProtein());
		assertEquals(7.2, entry.getFat());
		assertEquals(0, entry.getCarbohydrates());
		assertEquals(0, entry.getSugars());
		assertEquals(0, entry.getFibre());
		assertEquals(0.2, entry.getSalt());
	}
	@Test
	void searchFoodByBarcode_shouldReturnExistingFood() throws APIException, FoodNotFoundException {

		when(foodRepository.findByBarcode("123456789"))
				.thenReturn(food);

		Food result =
				foodApiService.searchFoodByBarcode("123456789");

		assertNotNull(result);
		assertEquals("Chicken Breast", result.getName());

		verify(foodRepository).findByBarcode("123456789");

		verify(openFoodFactsAPI, never())
				.searchFoodByBarcode(anyString());
	}

	@Test
	void searchFoodByBarcode_shouldCallAPIAndSaveFood()
			throws APIException, FoodNotFoundException {

		when(foodRepository.findByBarcode("123456789"))
				.thenReturn(null);

		when(openFoodFactsAPI.searchFoodByBarcode("123456789"))
				.thenReturn(food);

		when(foodRepository.save(food))
				.thenReturn(food);

		Food result =
				foodApiService.searchFoodByBarcode("123456789");

		assertNotNull(result);
		assertEquals("Chicken Breast", result.getName());

		verify(openFoodFactsAPI)
				.searchFoodByBarcode("123456789");

		verify(foodRepository)
				.save(food);
	}

	@Test
	void searchFoodByBarcode_shouldReturnNullWhenFoodNotFound()
			throws APIException, FoodNotFoundException {

		when(foodRepository.findByBarcode("999999"))
				.thenReturn(null);

		when(openFoodFactsAPI.searchFoodByBarcode("999999"))
				.thenThrow(new FoodNotFoundException("Food not found"));

		Food result =
				foodApiService.searchFoodByBarcode("999999");

		assertNull(result);

		verify(foodRepository, never()).save(any());
	}

	@Test
	void getBarcodeUsingName_shouldReturnExistingBarcode()
			throws Exception {

		when(foodRepository.findByName("Chicken Breast"))
				.thenReturn(food);

		String result =
				foodApiService.getBarcodeUsingName("Chicken Breast");

		assertEquals("123456789", result);

		verify(openFoodFactsAPI, never())
				.getBarcodeUsingName(anyString());
	}

	@Test
	void getBarcodeUsingName_shouldCallAPIWhenFoodDoesNotExist()
			throws Exception {

		when(foodRepository.findByName("Chicken Breast"))
				.thenReturn(null);

		when(openFoodFactsAPI.getBarcodeUsingName("Chicken Breast"))
				.thenReturn("123456789");

		String result =
				foodApiService.getBarcodeUsingName("Chicken Breast");

		assertEquals("123456789", result);

		verify(openFoodFactsAPI)
				.getBarcodeUsingName("Chicken Breast");
	}

	@Test
	void addDiaryEntry_shouldCreateEntry()
			throws Exception {

		when(userRepository.findByUserName("testuser"))
				.thenReturn(Optional.of(user));

		when(diaryRepository.findByClient(user))
				.thenReturn(diary);

		when(foodRepository.findByBarcode("123456789"))
				.thenReturn(food);

		when(diaryDayRepository.findByDiaryAndDate(
				diary,
				LocalDate.of(2026, 10, 8)))
				.thenReturn(diaryDay);

		when(diaryEntryRepository.save(any(DiaryEntry.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		DiaryEntry result =
				foodApiService.addDiaryEntry(
						"testuser",
						"123456789",
						200,
						LocalTime.of(12, 30),
						LocalDate.of(2026, 10, 8)
				);

		assertNotNull(result);

		assertEquals(200, result.getAmount());
		assertEquals(food, result.getFood());
		assertEquals(LocalTime.of(12, 30), result.getTime());

		// 200g of food containing 165 calories per 100g
		assertEquals(330, result.getCalories());

		assertEquals(62, result.getProtein());

		verify(diaryEntryRepository)
				.save(any(DiaryEntry.class));
	}

	@Test
	void addDiaryEntry_shouldThrowAccessDeniedWhenUserDoesNotExist() {

		when(userRepository.findByUserName("unknown"))
				.thenReturn(Optional.empty());

		assertThrows(
				AccessDeniedException.class,
				() -> foodApiService.addDiaryEntry(
						"unknown",
						"123456789",
						100,
						LocalTime.of(12, 30),
						LocalDate.of(2026, 10, 8)
				)
		);

		verify(diaryRepository, never())
				.findByClient(any());

		verify(diaryEntryRepository, never())
				.save(any());
	}

	@Test
	void getDiaryEntry_shouldReturnEntry() {

		when(userRepository.findByUserName("testuser"))
				.thenReturn(Optional.of(user));

		when(diaryEntryRepository.findById(1))
				.thenReturn(Optional.of(diaryEntry));

		DiaryEntry result =
				foodApiService.getDiaryEntry("testuser", 1);

		assertNotNull(result);
		assertEquals(diaryEntry, result);
	}

	@Test
	void getDiaryEntry_shouldThrowWhenEntryDoesNotExist() {

		when(userRepository.findByUserName("testuser"))
				.thenReturn(Optional.of(user));

		when(diaryEntryRepository.findById(999))
				.thenReturn(Optional.empty());

		assertThrows(
				EntityNotFoundException.class,
				() -> foodApiService.getDiaryEntry("testuser", 999)
		);
	}

	@Test
	void getDiaryEntriesForDay_shouldReturnEntries() {

		LocalDate date =
				LocalDate.of(2026, 10, 8);

		when(userRepository.findByUserName("testuser"))
				.thenReturn(Optional.of(user));

		when(diaryRepository.findByClient(user))
				.thenReturn(diary);

		when(diaryDayRepository.findByDiaryAndDate(
				diary,
				date))
				.thenReturn(diaryDay);

		List<DiaryEntry> result =
				foodApiService.getDiaryEntriesForDay(
						"testuser",
						date
				);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(diaryEntry, result.get(0));
	}

	@Test
	void updateDiaryEntry_shouldUpdateEntry()
			throws Exception {

		when(userRepository.findByUserName("testuser"))
				.thenReturn(Optional.of(user));

		when(diaryEntryRepository.findById(1))
				.thenReturn(Optional.of(diaryEntry));

		when(foodRepository.findByBarcode("123456789"))
				.thenReturn(food);

		when(diaryEntryRepository.save(any(DiaryEntry.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		DiaryEntry result =
				foodApiService.updateDiaryEntry(
						"testuser",
						1,
						"123456789",
						200,
						LocalTime.of(18, 0),
						LocalDate.of(2026, 10, 8)
				);

		assertEquals(200, result.getAmount());
		assertEquals(LocalTime.of(18, 0), result.getTime());

		assertEquals(330, result.getCalories());
		assertEquals(62, result.getProtein());

		verify(diaryEntryRepository)
				.save(diaryEntry);
	}

	@Test
	void deleteDiaryEntry_shouldDeleteEntry() {

		when(userRepository.findByUserName("testuser"))
				.thenReturn(Optional.of(user));

		when(diaryEntryRepository.findById(1))
				.thenReturn(Optional.of(diaryEntry));

		foodApiService.deleteDiaryEntry(
				"testuser",
				1
		);

		verify(diaryEntryRepository)
				.delete(diaryEntry);

		assertFalse(
				diaryDay.getEntries().contains(diaryEntry)
		);
	}


}

