package com.learningSpring.JournalAPI.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.learningSpring.JournalAPI.Entity.JournalEntry;
import com.learningSpring.JournalAPI.Entity.User;
import com.learningSpring.JournalAPI.Repository.JournalEntryRepository;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String username) {
        try {
            User existingUser = userService.getUserByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry =  journalEntryRepository.save(journalEntry);
            existingUser.getJournalEntries().add(savedEntry);
            userService.saveEntry(existingUser);
        } catch (Exception e) {
            throw new RuntimeException("Error saving journal entry: " + e.getMessage());
        }
        return journalEntry;
    }

    public void saveEntry(JournalEntry journalEntry) {
        try {
            journalEntryRepository.save(journalEntry);
        } catch (Exception e) {
            throw new RuntimeException("Error saving journal entry: " + e.getMessage());
        }
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteEntry(ObjectId id, String username) {
        try {
            User existingUser = userService.getUserByUsername(username);
            List<JournalEntry> journalEntries = existingUser.getJournalEntries();
            if (journalEntries != null && !journalEntries.isEmpty()) {
                journalEntries.removeIf(entry -> entry.getId().equals(id));
                existingUser.setJournalEntries(journalEntries);
                userService.saveEntry(existingUser);
            }
            journalEntryRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting journal entry: " + e.getMessage());
        }
    }
    
    public Optional<JournalEntry> updateEntry(ObjectId id, JournalEntry journalEntry) {
        try {
            JournalEntry existingEntry = journalEntryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Journal entry not found"));
            existingEntry.setTitle(journalEntry.getTitle());
            existingEntry.setContent(journalEntry.getContent());
            journalEntryRepository.save(existingEntry);
        } catch (Exception e) {
            throw new RuntimeException("Error updating journal entry: " + e.getMessage());
        }
        return null;
    }

}
