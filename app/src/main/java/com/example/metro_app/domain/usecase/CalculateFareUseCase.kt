package com.example.metro_app.domain.usecase

class CalculateFareUseCase {
    operator fun invoke(count: Int): Int {
        return when {
            count <= 9 -> 10
            count <= 20 -> 15
            else -> 20
        }
    }
}