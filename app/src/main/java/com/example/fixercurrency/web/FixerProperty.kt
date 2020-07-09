package com.example.fixercurrency.web

data class FixerProperty(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)