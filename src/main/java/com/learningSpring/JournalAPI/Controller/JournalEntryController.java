package com.learningSpring.JournalAPI.Controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learningSpring.JournalAPI.Entity.JournalEntry;
import com.learningSpring.JournalAPI.Entity.User;
import com.learningSpring.JournalAPI.Service.JournalEntryService;
import com.learningSpring.JournalAPI.Service.UserService;

@RestController
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @PostMapping("/users/{username}/journal-entries")
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntry, @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        JournalEntry createdEntry = journalEntryService.saveEntry(journalEntry, username);
        if (createdEntry != null){
            user.getJournalEntries().add(createdEntry);
            userService.saveEntry(user);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
    }

    @GetMapping("/users/{username}/journal-entries")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        List<JournalEntry> journalEntries = user.getJournalEntries();
        if (journalEntries != null && !journalEntries.isEmpty()) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No journal entries found for user.", HttpStatus.OK);
        }
    }

    @GetMapping("/journal-entries/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable("id") ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(id);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{username}/journal-entries/{id}")
    public ResponseEntity<Void> deleteJournalEntry(@PathVariable("id") ObjectId id, @PathVariable String username) {
        journalEntryService.deleteEntry(id, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/users/{username}/journal-entries/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable String username, @PathVariable("id") ObjectId id, @RequestBody JournalEntry journalEntry) {
        Optional<JournalEntry> updatedEntry = journalEntryService.updateEntry(id, journalEntry);
        if (updatedEntry != null && updatedEntry.isPresent()) {
            return new ResponseEntity<>(updatedEntry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
