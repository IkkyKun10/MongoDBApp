package com.riezki.mongoapp_springboot.controllers.model

data class SuccessDto(
    val message: String,
    val data: Any? = null
)