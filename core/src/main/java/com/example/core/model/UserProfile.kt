package com.example.core.model

data class UserProfile(
    val id: String,
    val fullName: String,
    val emailAddress: String,
    val address: String,
    val city: String,
    val state: String,
    val zipCode: String
)