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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learningSpring.JournalAPI.Entity.JournalEntry;
import com.learningSpring.JournalAPI.Entity.User;
import com.learningSpring.JournalAPI.Service.JournalEntryService;
import com.learningSpring.JournalAPI.Service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;
    
    @PostMapping("/{username}/create")
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

    @GetMapping("/all/{userName}")
    public ResponseEntity<?> getAllJournalEntriesofUser(@PathVariable String userName) {
        User user = userService.getUserByUsername(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries();
        if (journalEntries != null && !journalEntries.isEmpty()) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myid) {
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(myid);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{userName}/{myid}")
    public ResponseEntity<Void> deleteJournalEntry(@PathVariable ObjectId myid, @PathVariable String userName) {
        journalEntryService.deleteEntry(myid, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{userName}/{myid}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable String userName, @PathVariable ObjectId myid, @RequestBody JournalEntry journalEntry) {
        Optional<JournalEntry> updatedEntry = journalEntryService.updateEntry(myid, journalEntry);
        if (updatedEntry.isPresent()) {
            return new ResponseEntity<>(updatedEntry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
