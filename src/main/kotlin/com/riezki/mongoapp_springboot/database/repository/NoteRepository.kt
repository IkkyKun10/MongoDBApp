package com.riezki.mongoapp_springboot.database.repository

import com.riezki.mongoapp_springboot.database.model.Note
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface NoteRepository : MongoRepository<Note, ObjectId> {
    fun findByOwnerId(owner: ObjectId): List<Note>
    fun findByIdAndOwnerId(id: ObjectId, owner: ObjectId): Note?
}