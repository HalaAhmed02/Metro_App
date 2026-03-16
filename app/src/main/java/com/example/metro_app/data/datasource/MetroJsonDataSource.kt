package com.example.metro_app.data.datasource

import android.content.Context
import com.example.metro_app.data.dto.MetroDto
import com.example.metro_app.data.dto.StationDto
import com.google.gson.Gson
import java.io.File


class MetroJsonDataSource(
    private val context: Context
) : MetroDataSource {

    private val gson = Gson()

    private val dto by lazy {
        val json = context.assets
            .open("cairo_metro_structured.json")
            .bufferedReader()
            .use { it.readText() }

        gson.fromJson(json, MetroDto::class.java)
    }

    override fun loadStation(): List<StationDto> {
        return dto.stations
    }

    override fun getTravelTime(): Int {
        return dto.travel_time_between_stations_minutes
    }
}