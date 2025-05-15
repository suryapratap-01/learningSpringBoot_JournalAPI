package com.learningSpring.JournalAPI.Repository;

import org.bson.types.ObjectId;
import com.learningSpring.JournalAPI.Entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
