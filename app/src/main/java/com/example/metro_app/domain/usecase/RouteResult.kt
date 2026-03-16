package domain.usecase

import com.example.metro_app.domain.model.Station


sealed class RouteResult {
    data class Sucess(
        val station: List<Station>,
        val fare: Int,
        val time: Int
    ) : RouteResult()
    data class Error(val message: String): RouteResult()
}