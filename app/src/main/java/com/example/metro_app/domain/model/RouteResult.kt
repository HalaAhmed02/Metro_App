package com.example.metro_app.domain.model

sealed class RouteResult {
    data class Success(
        val stations: List<Station>,
        val fare: Int,
        val time: Int
    ) : RouteResult()

    data class Error(val message: String) : RouteResult()
}