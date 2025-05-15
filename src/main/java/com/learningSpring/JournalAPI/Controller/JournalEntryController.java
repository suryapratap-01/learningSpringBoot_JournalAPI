package com.learningSpring.JournalAPI.Controller;

import java.util.Optional;
import com.learningSpring.JournalAPI.Entity.JournalEntry;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.learningSpring.JournalAPI.Service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    
    @PostMapping("/create")
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntry) {
        JournalEntry createdEntry = journalEntryService.saveEntry(journalEntry);
        return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries() {
        List<JournalEntry> journalEntries = journalEntryService.getAllEntries();
        return new ResponseEntity<>(journalEntries, HttpStatus.OK);
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

    @DeleteMapping("/delete/{myid}")
    public ResponseEntity<Void> deleteJournalEntry(@PathVariable ObjectId myid) {
        boolean isDeleted = (boolean) journalEntryService.deleteEntry(myid);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{myid}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId myid, @RequestBody JournalEntry journalEntry) {
        Optional<JournalEntry> updatedEntry = journalEntryService.updateEntry(myid, journalEntry);
        if (updatedEntry.isPresent()) {
            return new ResponseEntity<>(updatedEntry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
