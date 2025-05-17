package com.learningSpring.JournalAPI.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.learningSpring.JournalAPI.Entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);
    void deleteByUserName(String userName);
}
