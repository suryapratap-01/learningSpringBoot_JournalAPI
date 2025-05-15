package com.learningSpring.JournalAPI.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.learningSpring.JournalAPI.Entity.JournalEntry;
import com.learningSpring.JournalAPI.Repository.JournalEntryRepository;
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry saveEntry(JournalEntry journalEntry) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalEntryRepository.save(journalEntry);
        } catch (Exception e) {
            throw new RuntimeException("Error saving journal entry: " + e.getMessage());
        }
        return journalEntry;
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public boolean deleteEntry(ObjectId id) {
        try {
            journalEntryRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting journal entry: " + e.getMessage());
        }
        return false;
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
