package com.example.metro_app.domain.repository

import com.example.metro_app.domain.model.Station

interface MetroRepository {
    fun getStations(): List<Station>
    fun getTravelTime(): Int
}