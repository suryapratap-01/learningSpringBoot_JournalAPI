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

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body("No such user exists");
        }
    }
    
    @PostMapping("/")
    public void createUser(@RequestBody User user) {
        userService.saveEntry(user);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        boolean deleted = userService.deleteUser(username);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(404).body("No such user exists");
        }
    }
    
    @PutMapping("/{username}")
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
