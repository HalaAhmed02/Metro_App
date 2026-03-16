package com.example.metro_app.domain.model

data class Station (
    val id: Int,
    val name: String,
    val line: MetroLine,
    val order: Int,
    val is_transfer: Boolean,
    val transferLines: List<MetroLine>
)