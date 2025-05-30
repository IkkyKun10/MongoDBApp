package com.riezki.mongoapp_springboot.controllers

import com.riezki.mongoapp_springboot.security.AuthService
import com.riezki.mongoapp_springboot.security.TokenPair
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    data class AuthRequest(
        @field:Email(message = "Email tidak valid")
        val email: String,
        @field:Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{9,}$",
            message = "Password harus memiliki minimal 9 karakter, 1 huruf besar, 1 huruf kecil, 1 angka dan 1 karakter spesial"
        )
        val password: String)
    data class RefreshRequest(val refreshToken: String)

    @PostMapping(path = ["/register"])
    fun register(
        @Valid @RequestBody body: AuthRequest
    ) {
        authService.register(body.email, body.password)
    }

    @PostMapping(path = ["/login"])
    fun login(
        @RequestBody body: AuthRequest
    ) : TokenPair {
        return authService.login(body.email, body.password)
    }

    @PostMapping(path = ["/refresh"])
    fun refresh(
        @RequestBody body: RefreshRequest
    ) : TokenPair {
        return authService.refresh(body.refreshToken)
    }


}