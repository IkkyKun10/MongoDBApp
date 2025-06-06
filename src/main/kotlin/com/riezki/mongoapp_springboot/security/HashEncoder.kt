package com.riezki.mongoapp_springboot.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class HashEncoder {

    private val bcrypt = BCryptPasswordEncoder()

    fun encode(raw: String) = bcrypt.encode(raw)
    fun matches(raw: String, hashed: String) = bcrypt.matches(raw, hashed)
}