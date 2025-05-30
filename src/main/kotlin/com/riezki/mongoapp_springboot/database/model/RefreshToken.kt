package com.riezki.mongoapp_springboot.database.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDateTime

@Document("refresh_token")
data class RefreshToken(
    val userId: ObjectId,
    @Indexed(expireAfter = "0s")
    val expiredAt: Instant,
    val hashedToken: String,
    val createAt: Instant = Instant.now(),
)
