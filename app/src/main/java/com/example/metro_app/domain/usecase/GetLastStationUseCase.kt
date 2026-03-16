package com.example.metro_app.domain.usecase

import com.example.metro_app.domain.model.MetroLine
import com.example.metro_app.domain.repository.MetroRepository

class GetLastStationUseCase (private val repository: MetroRepository){
   operator  fun invoke(line: MetroLine): String{
        return repository.getStations()
            .filter { it.line == line }
            .maxBy { it.order }
            .name
    }
}