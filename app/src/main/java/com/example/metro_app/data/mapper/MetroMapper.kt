package com.example.metro_app.data.mapper

import com.example.metro_app.data.dto.StationDto
import com.example.metro_app.domain.model.MetroLine
import com.example.metro_app.domain.model.Station

object MetroMapper {

    fun toDomain(dto: StationDto): Station {
        return Station(
            id = dto.id,
            name = dto.name,
            line = dto.line.toMetroLine(),
            order = dto.order,
            is_transfer = dto.is_transfer,
            transferLines = dto.transferLines?.map { it.toMetroLine() } ?: emptyList()
        )
    }

    private fun String.toMetroLine(): MetroLine =
        when (this.trim().uppercase()) {
            "LINE_1", "FIRST LINE", "1" -> MetroLine.LINE_1
            "LINE_2", "SECOND LINE", "2" -> MetroLine.LINE_2
            "LINE_3", "THIRD LINE", "3" -> MetroLine.LINE_3
            else -> throw IllegalArgumentException("Unknown line: $this")
        }
}