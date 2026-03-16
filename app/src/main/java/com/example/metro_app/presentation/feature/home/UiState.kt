package com.example.metro_app.presentation.feature.home

import com.example.metro_app.domain.model.Station

data class RecentRoute(
    val fromStation: String,
    val toStation: String
)

data class UiState(
    val startDestination: String = "",
    val endDestination: String = "",
    val destination: String = "",
    val route: List<Station> = emptyList(),
    val direction: String? = null,
    val fare: Int = 0,
    val time: Int = 0,
    val allStations: List<String> = emptyList(),
    val recentRoutes: List<RecentRoute> = emptyList()
)
