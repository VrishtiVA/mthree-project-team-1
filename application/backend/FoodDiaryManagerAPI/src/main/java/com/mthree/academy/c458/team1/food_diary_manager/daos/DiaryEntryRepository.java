package com.mthree.academy.c458.team1.food_diary_manager.daos;

import com.mthree.academy.c458.team1.food_diary_manager.models.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Integer> {
//	User findByUsername(String username);
//	Diary findByClient(User client);
//	DiaryDay findByDiaryAndDate(Diary diary, LocalDate date);
}
