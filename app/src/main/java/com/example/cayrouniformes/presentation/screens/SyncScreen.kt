package com.example.cayrouniformes.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.wear.tooling.preview.devices.WearDevices
import com.example.cayrouniformes.presentation.theme.CayroUniformesTheme
import kotlinx.coroutines.delay

@Composable
fun SyncScreen(navController: NavController) {
    var syncCode by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val primaryBlue = Color(0xFF3B82F6)
    val lightBackground = Color(0xFFF8FAFC)
    val darkText = Color(0xFF1E293B)
    val scrollState = rememberScrollState()

    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(2000)
            if (syncCode == "123456") {
                navController.navigate("notifications") {
                    popUpTo("welcome") { inclusive = true }
                }
            } else {
                error = "Código inválido"
                isLoading = false
            }
        }
    }

    LaunchedEffect(error) {
        if (error != null) {
            delay(1000)
            error = null
        }
    }

    Scaffold(
        timeText = {
            TimeText(modifier = Modifier.padding(top = 2.dp))
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(lightBackground),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sincronizar Cuenta",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = darkText,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = syncCode,
                    onValueChange = {
                        if (it.length <= 6) {
                            syncCode = it
                            error = null
                        }
                    },
                    label = { Text("Código", color = Color.Black) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = darkText,
                        cursorColor = primaryBlue,
                        focusedBorderColor = primaryBlue,
                        unfocusedBorderColor = darkText.copy(alpha = 0.5f),
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        disabledLabelColor = Color.Black
                    )
                )

                if (error != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = error ?: "",
                        color = Color.Red,
                        style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = { isLoading = true },
                    enabled = syncCode.length == 6 && !isLoading,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = primaryBlue,
                        contentColor = Color.White,
                        disabledBackgroundColor = primaryBlue,
                        disabledContentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "SINCRONIZAR",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Preview(device = WearDevices.SQUARE, showSystemUi = true)
@Composable

fun SyncScreen() {
    CayroUniformesTheme {
        WelcomeScreen(navController = rememberNavController())
    }
}