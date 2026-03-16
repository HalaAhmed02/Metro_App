package com.example.metro_app.presentation.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

val RedPrimary = Color(0xFFE53935)
val BlackBackground = Color(0xFF121212)
val GraySurface = Color(0xFF1E1E1E)

@Composable
fun HomeScreen(
    uiState: UiState,
    onFindRouteClick: (String, String) -> Unit,
    onNavigateToDetails: (String, String) -> Unit,
    onRemoveRecentRoute: (RecentRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    var startStation by remember { mutableStateOf("") }
    var endStation by remember { mutableStateOf("") }
    var showStartDialog by remember { mutableStateOf(false) }
    var showEndDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = BlackBackground
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .background(BlackBackground)
                .statusBarsPadding()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(RedPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.DirectionsSubway, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "CAIRO METRO",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Where are you going?",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(GraySurface)
                        .padding(16.dp)
                ) {
                    StationSelector(
                        label = "From Station",
                        selectedStation = startStation,
                        onClick = { showStartDialog = true }
                    )

                    Box(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.White.copy(alpha = 0.1f))
                    )

                    StationSelector(
                        label = "To Station",
                        selectedStation = endStation,
                        onClick = { showEndDialog = true }
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 32.dp)
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(RedPrimary)
                        .clickable {
                            val temp = startStation
                            startStation = endStation
                            endStation = temp
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.SwapVert, contentDescription = "Swap", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (startStation.isNotBlank() && endStation.isNotBlank()) {
                        onFindRouteClick(startStation, endStation)
                        onNavigateToDetails(startStation, endStation)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Search Route", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            if (uiState.recentRoutes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(40.dp))


                Text(
                    text = "Recent Routes",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                uiState.recentRoutes.asReversed().forEach { route ->
                    RecentItem(
                        route = route,
                        onClick = {
                            startStation = route.fromStation
                            endStation = route.toStation
                        },
                        onDelete = { onRemoveRecentRoute(route) }
                    )
                }
            }
        }
    }

    if (showStartDialog) {
        StationSelectionDialog(
            stations = uiState.allStations,
            onStationSelected = {
                startStation = it
                showStartDialog = false
            },
            onDismiss = { showStartDialog = false }
        )
    }

    if (showEndDialog) {
        StationSelectionDialog(
            stations = uiState.allStations,
            onStationSelected = {
                endStation = it
                showEndDialog = false
            },
            onDismiss = { showEndDialog = false }
        )
    }
}

@Composable
fun StationSelector(
    label: String,
    selectedStation: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.LocationOn,
            contentDescription = null,
            tint = if (selectedStation.isEmpty()) Color.Gray else RedPrimary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
            Text(
                text = if (selectedStation.isEmpty()) "Select Station" else selectedStation,
                color = if (selectedStation.isEmpty()) Color.Gray else Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun RecentItem(
    route: RecentRoute,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = GraySurface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.History, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${route.fromStation} → ${route.toStation}",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    maxLines = 1
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.Gray, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun StationSelectionDialog(
    stations: List<String>,
    onStationSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = GraySurface,
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Select Station",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                LazyColumn {
                    items(stations) { station ->
                        Text(
                            text = station,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onStationSelected(station) }
                                .padding(vertical = 16.dp, horizontal = 8.dp),
                            fontSize = 17.sp,
                            color = Color.White
                        )
                        HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
                    }
                }
            }
        }
    }
}
