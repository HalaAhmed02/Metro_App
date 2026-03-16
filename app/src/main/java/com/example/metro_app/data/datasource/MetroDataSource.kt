package com.example.metro_app.data.datasource

import com.example.metro_app.data.dto.StationDto

interface MetroDataSource {
    fun loadStation(): List<StationDto>
    fun getTravelTime(): Int
}