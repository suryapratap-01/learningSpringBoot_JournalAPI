package com.learningSpring.JournalAPI.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.learningSpring.JournalAPI.Entity.User;
import com.learningSpring.JournalAPI.Repository.UserRepository;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(com.learningSpring.JournalAPI.Entity.User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public boolean deleteUser(String username) {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            userRepository.delete(user);
            return true;
        } else {
            return false;
        }
    }

    public void findByUserName(String userName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
