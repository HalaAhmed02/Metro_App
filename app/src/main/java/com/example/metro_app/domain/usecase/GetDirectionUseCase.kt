package com.example.metro_app.domain.usecase

import com.example.metro_app.domain.model.Station

class GetDirectionUseCase(
    private val getFirstStationUseCase: GetFirstStationUseCase,
    private val getLastStationUseCase: GetLastStationUseCase
) {

    operator fun invoke(
        current: Station,
        next: Station
    ): String {

        val first = getFirstStationUseCase.execute(current.line)
        val last = getLastStationUseCase(current.line)

        return if (next.order > current.order) {
            last
        } else {
            first
        }
    }
}