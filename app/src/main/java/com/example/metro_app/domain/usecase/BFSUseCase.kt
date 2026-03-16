package com.example.metro_app.domain.usecase

import com.example.metro_app.domain.model.MetroLine
import com.example.metro_app.domain.model.Station
import java.util.*

class BFSUseCase {

    operator fun invoke(
        start: Station,
        end: Station,
        stations: List<Station>
    ): List<Station>? {

        val queue: Queue<List<Station>> = LinkedList()
        // Unique state is (name + line) or (id + line)
        val visited = mutableSetOf<Pair<String, MetroLine>>()

        queue.add(listOf(start))

        while (queue.isNotEmpty()) {
            val path = queue.poll() ?: continue
            val current = path.last()

            // Success condition: check if we reached any station with the same normalized name
            if (current.name.normalize() == end.name.normalize()) {
                return path
            }

            val state = current.name.normalize() to current.line
            if (visited.contains(state)) continue
            visited.add(state)

            val neighbors = getNeighbors(current, stations)

            neighbors.forEach { neighbor ->
                if (!visited.contains(neighbor.name.normalize() to neighbor.line)) {
                    queue.add(path + neighbor)
                }
            }
        }

        return null
    }

    private fun getNeighbors(
        station: Station,
        stations: List<Station>
    ): List<Station> {

        val normalizedCurrentName = station.name.normalize()

        // 1. Same line neighbors: adjacent by order
        val sameLine = stations.filter {
            it.line == station.line &&
                    (it.order == station.order + 1 || it.order == station.order - 1)
        }

        // 2. Transfer neighbors: stations with the same normalized name but on different lines
        val transfers = stations.filter { s ->
            s.name.normalize() == normalizedCurrentName && s.line != station.line
        }

        return (sameLine + transfers).distinct()
    }

    // Helper to handle naming variations like "Al-Shohadaa" vs "Shohadaa" or "El Maadi"
    private fun String.normalize(): String {
        return this.lowercase()
            .trim()
            .replace("-", " ")
            .replace("el ", "")
            .replace("al ", "")
            .replace("'", "")
            .replace("  ", " ")
            .trim()
    }
}
