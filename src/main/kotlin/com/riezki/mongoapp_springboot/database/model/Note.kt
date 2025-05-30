package com.riezki.mongoapp_springboot.database.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("note")
data class Note(
    val title: String,
    val content: String,
    val color: Long,
    val createAt: Instant = Instant.now(),
    val ownerId: ObjectId,
    @Id val id: ObjectId = ObjectId.get()
)
