package com.mthree.academy.c458.team1.food_diary_manager.daos;

import com.mthree.academy.c458.team1.food_diary_manager.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

	public Food findByName(String foodName);
	public Food findByBarcode(String barcode);
}
