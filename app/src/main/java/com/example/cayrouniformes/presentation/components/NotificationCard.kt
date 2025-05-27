package com.example.cayrouniformes.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.cayrouniformes.presentation.theme.CayroBlue
import com.example.cayrouniformes.presentation.theme.CayroGold
import com.example.cayrouniformes.presentation.theme.CayroRed
import com.example.cayrouniformes.presentation.theme.Success
import com.example.cayrouniformes.presentation.theme.Warning
import androidx.compose.ui.graphics.graphicsLayer

enum class OrderStatus {
    PROCESSING,
    READY,
    SHIPPED,
    DELIVERED,
    DELAYED
}

@Composable
fun StatusIndicator(
    status: OrderStatus,
    modifier: Modifier = Modifier
) {
    val color = when (status) {
        OrderStatus.PROCESSING -> CayroBlue
        OrderStatus.READY -> CayroGold
        OrderStatus.SHIPPED -> Warning
        OrderStatus.DELIVERED -> Success
        OrderStatus.DELAYED -> CayroRed
    }

    Box(
        modifier = modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(color)
            .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
    )
}

@Composable
fun NotificationCard(
    orderId: String,
    timestamp: String,
    message: String,
    status: OrderStatus,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by scaleAnimation(if (pressed) 0.97f else 1f)

    val backgroundColor = Color(0xFFF8FAFC) // fondo claro
    val borderColor = when (status) {
        OrderStatus.PROCESSING -> CayroBlue
        OrderStatus.READY -> CayroGold
        OrderStatus.SHIPPED -> Warning
        OrderStatus.DELIVERED -> Success
        OrderStatus.DELAYED -> CayroRed
    }.copy(alpha = 0.6f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                pressed = true
                onClick()
                pressed = false
            }
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusIndicator(status = status)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Orden #$orderId",
                    style = MaterialTheme.typography.title3,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = borderColor,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.body2,
                color = Color.DarkGray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = timestamp,
                style = MaterialTheme.typography.caption2,
                color = Color.Gray
            )
        }
    }
}
