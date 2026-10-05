package com.mthree.academy.c458.team1.food_diary_manager.daos;

import com.mthree.academy.c458.team1.food_diary_manager.models.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
}
