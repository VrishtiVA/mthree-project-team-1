package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.daos.UserRepository;
import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.models.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultantServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ConsultantServiceImpl consultantService;

    @Test
    void getConsultantByIdIsConsultant() {

        //Arrange
        User user = new User();
        user.setRole(UserRole.CONSULTANT);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        //Act
        User retrievedUser = consultantService.getConsultantById(2);
        //Assert
        assertEquals(user, retrievedUser);
    }

    @Test
    void getConsultantByIdIsNotConsultant() {

        //Arrange
        User user = new User();
        user.setRole(UserRole.CLIENT);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        //Act
        User retrievedUser = consultantService.getConsultantById(2);
        //Assert
        assertNull(retrievedUser);
    }

    @Test
    void getConsultantByIdNoUser() {

        //Arrange
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        //Act
        User retrievedUser = consultantService.getConsultantById(2);
        //Assert
        assertNull(retrievedUser);
    }

    @Test
    void addClientToConsultant() {

    }

    @Test
    void getAllClients() {

    }

    @Test
    void removeClientFromConsultant() {

    }
}