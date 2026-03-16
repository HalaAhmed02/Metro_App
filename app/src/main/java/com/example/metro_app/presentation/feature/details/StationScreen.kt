package com.example.metro_app.presentation.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsTransit
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.metro_app.presentation.feature.home.HomeViewModel

val RedPrimary = Color(0xFFE53935)
val BlackBackground = Color(0xFF121212)
val GraySurface = Color(0xFF1E1E1E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationScreen(
    startStation: String,
    endStation: String,
    viewModel: HomeViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(startStation, endStation) {
        viewModel.findRoute(startStation, endStation)
    }

    Scaffold(
        containerColor = BlackBackground,
        topBar = {
            TopAppBar(
                title = { Text("Trip Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlackBackground,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailCard(
                    icon = Icons.Default.DirectionsTransit,
                    label = "Stations",
                    value = "${uiState.route.size}",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                DetailCard(
                    icon = Icons.Default.Payments,
                    label = "Price",
                    value = "${uiState.fare} EGP",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                DetailCard(
                    icon = Icons.Default.Timer,
                    label = "Time",
                    value = "${uiState.time} min",
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "Route Path",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                itemsIndexed(uiState.route) { index, station ->
                    val isFirst = index == 0
                    val isLast = index == uiState.route.size - 1
                    
                    RouteStep(
                        stationName = station.name,
                        lineName = station.line.name,
                        isTransfer = station.is_transfer,
                        isFirst = isFirst,
                        isLast = isLast
                    )
                }
            }
        }
    }
}

@Composable
fun DetailCard(icon: ImageVector, label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = GraySurface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = RedPrimary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = label, color = Color.Gray, fontSize = 11.sp)
        }
    }
}

@Composable
fun RouteStep(
    stationName: String,
    lineName: String,
    isTransfer: Boolean,
    isFirst: Boolean,
    isLast: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp)
        ) {
                        Box(
                modifier = Modifier
                    .width(4.dp)
                    .weight(1f)
                    .background(if (isFirst) Color.Transparent else RedPrimary)
            )
            Box(
                modifier = Modifier
                    .size(if (isTransfer) 16.dp else 12.dp)
                    .clip(CircleShape)
                    .background(if (isTransfer) Color.White else RedPrimary)
                    .then(if (isTransfer) Modifier.padding(2.dp) else Modifier)
            ) {
                if (isTransfer) {
                    Box(modifier = Modifier.fillMaxSize().clip(CircleShape).background(RedPrimary))
                }
            }
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .weight(1f)
                    .background(if (isLast) Color.Transparent else RedPrimary)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stationName,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = if (isTransfer || isFirst || isLast) FontWeight.Bold else FontWeight.Medium
            )
            if (isTransfer) {
                Text(
                    text = "Transfer Station",
                    color = RedPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = lineName,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }
    }
}
