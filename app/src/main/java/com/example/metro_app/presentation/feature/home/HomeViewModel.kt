package com.example.metro_app.presentation.feature.home

import androidx.lifecycle.ViewModel
import com.example.metro_app.domain.model.RouteResult
import com.example.metro_app.domain.repository.MetroRepository
import com.example.metro_app.domain.usecase.FindRouteUseCase
import com.example.metro_app.domain.usecase.GetDirectionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val findRouteUseCase: FindRouteUseCase,
    private val getDirectionUseCase: GetDirectionUseCase,
    private val repository: MetroRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadStations()
    }

    private fun loadStations() {
        val stations = repository.getStations()
            .map { it.name }
            .distinct()
            .sorted()
        _uiState.update { it.copy(allStations = stations) }
    }

    fun findRoute(from: String, to: String) {
        val result = findRouteUseCase(from, to)

        if (result is RouteResult.Success) {
            val route = result.stations
            var direction: String? = null

            if (route.size >= 2) {
                val current = route[0]
                val next = route[1]
                direction = getDirectionUseCase(current, next)
            }

            _uiState.update { state ->
                val newRecent = (state.recentRoutes + RecentRoute(from, to))
                    .distinct()
                    .takeLast(5)
                
                state.copy(
                    startDestination = from,
                    endDestination = to,
                    route = route,
                    direction = direction,
                    fare = result.fare,
                    time = result.time,
                    recentRoutes = newRecent
                )
            }
        } else if (result is RouteResult.Error) {
            _uiState.update { it.copy(route = emptyList()) }
        }
    }

    fun removeRecentRoute(route: RecentRoute) {
        _uiState.update { state ->
            state.copy(recentRoutes = state.recentRoutes.filter { it != route })
        }
    }
}