package com.example.apnabazaar.model

data class UserModel(
    val name: String="",
    val email: String="",
    val userId: String="",
    // we should not store password , it should be handled by authentication
    val cartItem : Map<String,Long> = emptyMap(),
    val address: String=""
)
