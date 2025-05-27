    package com.example.cayrouniformes.presentation.screens

    import androidx.compose.animation.core.*
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.ArrowForward
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.alpha
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.draw.scale
    import androidx.compose.ui.graphics.Brush
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController
    import androidx.navigation.compose.rememberNavController
    import androidx.wear.compose.material.*
    import androidx.wear.tooling.preview.devices.WearDevices
    import com.example.cayrouniformes.R
    import com.example.cayrouniformes.presentation.theme.CayroUniformesTheme
    import kotlinx.coroutines.delay

    @Composable
    fun WelcomeScreen(navController: NavController) {
        var logoVisible by remember { mutableStateOf(false) }
        var titleVisible by remember { mutableStateOf(false) }
        var buttonVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            logoVisible = true
            delay(300)
            titleVisible = true
            delay(300)
            buttonVisible = true
        }

        val primaryBlue = Color(0xFF3B82F6)
        val secondaryBlue = Color(0xFF60A5FA)
        val lightBackground = Color(0xFFF8FAFC)
        val darkText = Color(0xFF1E293B)
        val gradient = Brush.verticalGradient(
            colors = listOf(primaryBlue, secondaryBlue)
        )

        Scaffold(
            timeText = {
                TimeText(
                    modifier = Modifier
                        .scale(0.8f)
                        .padding(top = 2.dp)
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(lightBackground),
                contentAlignment = Alignment.Center
            ) {
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    FadeInAnimation(visible = logoVisible) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .width(140.dp)
                                .height(70.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(gradient)
                                .padding(12.dp)
                        ) {
                            PulseAnimation {
                                Image(
                                    painter = painterResource(id = R.drawable.logo_cayro),
                                    contentDescription = "Logo Cayro",
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    SlideInAnimation(visible = titleVisible) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = "BIENVENIDO A",
                                style = TextStyle(
                                    color = darkText.copy(alpha = 0.8f),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "CAYRO UNIFORMES",
                                style = TextStyle(
                                    color = primaryBlue,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Sistema de gestión de pedidos",
                                style = TextStyle(
                                    color = darkText.copy(alpha = 0.6f),
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    FadeInAnimation(visible = buttonVisible) {
                        Button(
                            onClick = { navController.navigate("sync") },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(36.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = primaryBlue,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "COMENZAR",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        letterSpacing = 0.5.sp
                                    )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Continuar",
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun FadeInAnimation(
        visible: Boolean,
        delay: Long = 0,
        content: @Composable () -> Unit
    ) {
        val alpha by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = delay.toInt(),
                easing = FastOutSlowInEasing
            ), label = "fadeIn"
        )

        Box(modifier = Modifier.alpha(alpha)) {
            content()
        }
    }

    @Composable
    fun SlideInAnimation(
        visible: Boolean,
        content: @Composable () -> Unit
    ) {
        val offsetX by animateFloatAsState(
            targetValue = if (visible) 0f else 50f,
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            ), label = "slideIn"
        )

        Box(
            modifier = Modifier
                .alpha(if (visible) 1f else 0f)
                .offset(x = offsetX.dp)
        ) {
            content()
        }
    }

    @Composable
    fun PulseAnimation(content: @Composable () -> Unit) {
        val infiniteTransition = rememberInfiniteTransition(label = "pulseTransition")
        val pulse by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(1500, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ), label = "pulse"
        )

        Box(modifier = Modifier.scale(pulse)) {
            content()
        }
    }

    @Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
    @Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
    @Preview(device = WearDevices.SQUARE, showSystemUi = true)
    @Composable
    fun WelcomeScreenPreview() {
        CayroUniformesTheme {
            WelcomeScreen(navController = rememberNavController())
        }
    }