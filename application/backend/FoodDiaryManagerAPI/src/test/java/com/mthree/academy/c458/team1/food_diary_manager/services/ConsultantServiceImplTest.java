package com.mthree.academy.c458.team1.food_diary_manager.services;

import com.mthree.academy.c458.team1.food_diary_manager.daos.UserRepository;
import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import com.mthree.academy.c458.team1.food_diary_manager.models.UserRole;
import com.mthree.academy.c458.team1.food_diary_manager.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    void testGetConsultantByIdIsConsultant() {

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
    void testGetConsultantByIdIsNotConsultant() {

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
    void testGetConsultantByIdNoUser() {

        //Arrange
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        //Act
        User retrievedUser = consultantService.getConsultantById(2);
        //Assert
        assertNull(retrievedUser);
    }

    @Test
    void testAddClientToConsultantGetClients() {

        //Arrange 1 consultant and 2 clients
        User client1 = new User();
        client1.setUserId(1);
        client1.setRole(UserRole.CLIENT);
        User consultant1 = new User();
        consultant1.setUserId(2);
        consultant1.setRole(UserRole.CONSULTANT);
        User client2 = new User();
        client2.setUserId(3);
        client2.setRole(UserRole.CLIENT);
        when(userRepository.findById(1)).thenReturn(Optional.of(client1));
        when(userRepository.findById(2)).thenReturn(Optional.of(consultant1));
        when(userRepository.findById(3)).thenReturn(Optional.of(client2));

        //Act & Assert adding 1 okay
        consultantService.addClientToConsultant(1, 2);
        List<User> clients = consultantService.getAllClients(2);
        assertEquals(1, clients.size());
        assertTrue(clients.contains(client1));

        //Act & Assert adding 1 again doesn't cause duplicate
        consultantService.addClientToConsultant(1, 2);
        clients = consultantService.getAllClients(2);
        assertEquals(1, clients.size());
        assertTrue(clients.contains(client1));

        //Act & Assert adding 2 okay
        consultantService.addClientToConsultant(3, 2);
        clients = consultantService.getAllClients(2);
        assertEquals(2, clients.size());
        assertTrue(clients.contains(client1));
        assertTrue(clients.contains(client2));
    }

    @Test
    void testAddClientToConsultantNotConsultant() {

        //Arrange 2 clients
        User client1 = new User();
        client1.setUserId(1);
        client1.setRole(UserRole.CLIENT);
        User consultant1 = new User();
        consultant1.setUserId(2);
        consultant1.setRole(UserRole.CLIENT);
        when(userRepository.findById(2)).thenReturn(Optional.of(consultant1));

        //Act & Assert - Adding a client to a client should not be possible
        try {
            consultantService.addClientToConsultant(1, 2);
            fail("User not found exception should have been thrown");
        } catch (UserNotFoundException ex) { return;
        } catch (Exception ex) {
            fail("The incorrect exception was thrown");
        }
    }

    @Test
    void testGetAllClientsEmpty() {

        //Arrange
        User consultant1 = new User();
        consultant1.setUserId(1);
        consultant1.setRole(UserRole.CONSULTANT);
        when(userRepository.findById(1)).thenReturn(Optional.of(consultant1));

        //Act
        List<User> clients = consultantService.getAllClients(1);

        //Assert
        assertNotNull(clients);
        assertEquals(0, clients.size());
    }

    @Test
    void testRemoveClientFromConsultant() {

        //Arrange 1 consultant and 2 clients
        User client1 = new User();
        client1.setUserId(1);
        client1.setRole(UserRole.CLIENT);
        User consultant1 = new User();
        consultant1.setUserId(2);
        consultant1.setRole(UserRole.CONSULTANT);
        User client2 = new User();
        client2.setUserId(3);
        client2.setRole(UserRole.CLIENT);
        when(userRepository.findById(1)).thenReturn(Optional.of(client1));
        when(userRepository.findById(2)).thenReturn(Optional.of(consultant1));
        when(userRepository.findById(3)).thenReturn(Optional.of(client2));

        //Arrange added clients
        consultantService.addClientToConsultant(1, 2);
        List<User> clients = consultantService.getAllClients(2);
        assertEquals(1, clients.size());
        consultantService.addClientToConsultant(3, 2);
        clients = consultantService.getAllClients(2);

        //Assert - ready to go
        assertEquals(2, clients.size(), "Test should be arranged in a good known state");
        assertTrue(clients.contains(client1), "Test should be arranged in a good known state");
        assertTrue(clients.contains(client2), "Test should be arranged in a good known state");

        //Act & Assert - remove 1
        consultantService.removeClientFromConsultant(1, 2);
        assertEquals(1, clients.size());
        assertFalse(clients.contains(client1));
        assertTrue(clients.contains(client2));

        //Act & Assert - remove again
        consultantService.removeClientFromConsultant(1, 2);
        assertEquals(1, clients.size());
        assertFalse(clients.contains(client1));
        assertTrue(clients.contains(client2));

        //Act and Assert - remove remaining
        consultantService.removeClientFromConsultant(3, 2);
        assertEquals(0, clients.size());
    }

}