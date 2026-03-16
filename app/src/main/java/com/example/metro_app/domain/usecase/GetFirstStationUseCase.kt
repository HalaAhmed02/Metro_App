package com.example.metro_app.domain.usecase

import com.example.metro_app.domain.model.MetroLine
import com.example.metro_app.domain.repository.MetroRepository

class GetFirstStationUseCase (private val repository: MetroRepository) {
    fun execute(line: MetroLine): String {
        return repository.getStations()
            .filter { it.line == line }
            .minBy { it.order }
            .name
    }
}