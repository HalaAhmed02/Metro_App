package com.example.metro_app.data.dto

data class StationDto(
    val id: Int,
    val name: String,
    val line: String,
    val order: Int,
    val is_transfer: Boolean,
    val transferLines: List<String>? = null
)