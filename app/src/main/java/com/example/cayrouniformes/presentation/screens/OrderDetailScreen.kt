package com.example.cayrouniformes.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.*
import com.example.cayrouniformes.presentation.components.OrderStatus
import com.example.cayrouniformes.presentation.components.StatusIndicator

@Composable
fun OrderDetailScreen(
    navController: NavController,
    orderId: String
) {
    val orderStatus = OrderStatus.READY

    val primaryBlue = Color(0xFF3B82F6)
    val secondaryBlue = Color(0xFF60A5FA)
    val lightBackground = Color(0xFFF8FAFC)
    val gradient = Brush.verticalGradient(listOf(primaryBlue, secondaryBlue))
    val darkText = Color(0xFF1E293B)

    Scaffold(
        timeText = {
            TimeText(modifier = Modifier.padding(top = 2.dp))
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(lightBackground)
        ) {
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    end = 12.dp,
                    top = 52.dp,
                    bottom = 12.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(gradient)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { navController.navigateUp() },
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = primaryBlue
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Orden #$orderId",
                            style = MaterialTheme.typography.title2,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        StatusIndicator(status = orderStatus)

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = when (orderStatus) {
                                OrderStatus.PROCESSING -> "En procesamiento"
                                OrderStatus.READY -> "Listo para recoger"
                                OrderStatus.SHIPPED -> "Enviado"
                                OrderStatus.DELIVERED -> "Entregado"
                                OrderStatus.DELAYED -> "Retrasado"
                            },
                            style = MaterialTheme.typography.body1,
                            color = darkText
                        )
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "Detalles del pedido",
                                style = MaterialTheme.typography.title3,
                                color = primaryBlue,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            OrderDetailRow("Fecha:", "20 Mayo 2025")
                            OrderDetailRow("Artículos:", "3")
                            OrderDetailRow("Entrega:", "En tienda")
                            OrderDetailRow("Estado:", "Pago completado")
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "¡Tu pedido está listo para recoger en tienda!",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        color = darkText,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = primaryBlue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Confirmar recogida",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.button,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun OrderDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )

        Text(
            text = value,
            style = MaterialTheme.typography.body2,
            color = Color.Black
        )
    }
}
