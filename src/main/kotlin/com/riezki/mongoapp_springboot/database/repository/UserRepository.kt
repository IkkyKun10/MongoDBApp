package com.riezki.mongoapp_springboot.database.repository

import com.riezki.mongoapp_springboot.database.model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, ObjectId> {
    fun findByEmail(email: String) : User?
}