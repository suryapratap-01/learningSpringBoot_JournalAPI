package com.learningSpring.JournalAPI.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learningSpring.JournalAPI.Entity.User;
import com.learningSpring.JournalAPI.Service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        userService.saveEntry(user);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody User user) {
        userService.deleteUser(user.getUserName());
    }
    
    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username) {
        try {
            User userInDb = userService.getUserByUsername(username);
            if (userInDb == null) {
                return ResponseEntity.status(404).body("User not found");
            } else {
                userInDb.setUserName(user.getUserName());
                userInDb.setPassword(user.getPassword());
                userService.saveEntry(userInDb);
                return ResponseEntity.ok(userInDb);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while updating the user: " + e.getMessage());
        }
    }

}
