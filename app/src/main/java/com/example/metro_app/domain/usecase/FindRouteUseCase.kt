package com.example.metro_app.domain.usecase

import com.example.metro_app.domain.model.RouteResult
import com.example.metro_app.domain.repository.MetroRepository


class FindRouteUseCase(
    private val repo: MetroRepository,
    private val calculateFareUseCase: CalculateFareUseCase,
    private val calculateTimeUseCase: CalculateTimeUseCase,
    private val bfsUseCase: BFSUseCase
) {

    operator fun invoke(
        startName: String,
        endName: String
    ): RouteResult {

        val stations = repo.getStations()

        val start = stations.find { it.name.equals(startName.trim(), ignoreCase = true) }
            ?: return RouteResult.Error("Start station not found")

        val end = stations.find { it.name.equals(endName.trim(), ignoreCase = true) }
            ?: return RouteResult.Error("End station not found")

        val path = bfsUseCase(start, end, stations)
            ?: return RouteResult.Error("No route found")

        val stationCount = path.size
        val fare = calculateFareUseCase(stationCount)
        val time = calculateTimeUseCase(stationCount - 1)

        return RouteResult.Success(
            stations = path,
            fare = fare,
            time = time
        )
    }
}