package com.example.metro_app.data.dto

data class MetroDto(
    val stations: List<StationDto>,
    val travel_time_between_stations_minutes : Int
)