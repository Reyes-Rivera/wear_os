package com.example.cayrouniformes.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.*
import com.example.cayrouniformes.R
import com.example.cayrouniformes.presentation.components.NotificationCard
import com.example.cayrouniformes.presentation.components.OrderStatus
import kotlinx.coroutines.delay

data class OrderNotification(
    val id: String,
    val message: String,
    val timestamp: String,
    val status: OrderStatus
)

@Composable
fun NotificationsScreen(navController: NavController) {
    val haptic = LocalHapticFeedback.current
    var isRefreshing by remember { mutableStateOf(false) }
    var notifications by remember {
        mutableStateOf(
            listOf(
                OrderNotification("2305", "¡Tu pedido está listo para recoger!", "Hace 30 min", OrderStatus.READY),
                OrderNotification("2298", "Tu pedido ha sido enviado", "Hace 2 horas", OrderStatus.SHIPPED),
                OrderNotification("2290", "Pedido entregado", "Ayer, 15:30", OrderStatus.DELIVERED),
                OrderNotification("2287", "Retraso en producción", "18 May, 10:15", OrderStatus.DELAYED),
                OrderNotification("2275", "Pedido en procesamiento", "15 May, 09:22", OrderStatus.PROCESSING)
            )
        )
    }

    // Acción de refrescar
    val onRefresh = {
        if (!isRefreshing) {
            isRefreshing = true
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    // Simular actualización
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(2000)
            notifications = listOf(
                OrderNotification("2312", "Nuevo pedido recibido", "Ahora mismo", OrderStatus.PROCESSING)
            ) + notifications
            isRefreshing = false
        }
    }

    // Colores
    val primaryBlue = Color(0xFF3B82F6)
    val secondaryBlue = Color(0xFF60A5FA)
    val whiteBackground = Color.White
    val headerGradient = Brush.verticalGradient(listOf(primaryBlue, secondaryBlue))

    Scaffold(
        timeText = { TimeText(modifier = Modifier.padding(top = 2.dp)) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(whiteBackground)
        ) {
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    top = 52.dp,
                    bottom = 8.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Header con logo + texto más delgado
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(headerGradient)
                            .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp), // menos alto
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_cayro),
                                contentDescription = "Logo Cayro",
                                modifier = Modifier
                                    .size(80.dp) // ya más compacto
                                    .padding(bottom = 2.dp)
                            )
                            Text(
                                text = "BIENVENIDO REYES",
                                style = MaterialTheme.typography.caption1,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }


                // Botón de refrescar
                item {
                    CompactChip(
                        onClick = onRefresh,
                        label = {
                            Text(text = "TUS NOTIFICACIONES")
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refrescar"
                            )
                        },
                        colors = ChipDefaults.primaryChipColors(),
                        enabled = !isRefreshing,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (isRefreshing) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Lista de notificaciones
                if (notifications.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "NO HAY NOTIFICACIONES",
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(notifications) { notification ->
                        NotificationCard(
                            orderId = notification.id,
                            timestamp = notification.timestamp,
                            message = notification.message,
                            status = notification.status,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                navController.navigate("order/${notification.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}
