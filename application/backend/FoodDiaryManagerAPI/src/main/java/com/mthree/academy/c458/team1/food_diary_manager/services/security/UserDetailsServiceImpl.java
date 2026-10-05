package com.mthree.academy.c458.team1.food_diary_manager.services.security;

import com.mthree.academy.c458.team1.food_diary_manager.daos.UserRepository;
import com.mthree.academy.c458.team1.food_diary_manager.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService implementation for Spring Security.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param username the username identifying the user whose data is required.
     * @return The user details object of the user to authenticate.
     * @throws UsernameNotFoundException If the user cannot be found by username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Load user
        User user = userRepository.findByUserName(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User with this username not found.");
        }

        //Return as user details object
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities(user.getRole().name()) //Client or Consultant
                .build();
    }
}
