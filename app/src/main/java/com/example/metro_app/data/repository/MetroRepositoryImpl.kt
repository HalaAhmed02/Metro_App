package com.example.metro_app.data.repository

import com.example.metro_app.data.datasource.MetroDataSource
import com.example.metro_app.data.mapper.MetroMapper
import com.example.metro_app.domain.model.Station
import com.example.metro_app.domain.repository.MetroRepository

class MetroRepositoryImpl(
    private val dataSource: MetroDataSource
) : MetroRepository {

    override fun getStations(): List<Station> =
        dataSource.loadStation()
            .map { MetroMapper.toDomain(it) }

    override fun getTravelTime(): Int = dataSource.getTravelTime()
}