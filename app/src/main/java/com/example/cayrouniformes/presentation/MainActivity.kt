package com.example.cayrouniformes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cayrouniformes.presentation.screens.NotificationsScreen
import com.example.cayrouniformes.presentation.screens.OrderDetailScreen
import com.example.cayrouniformes.presentation.screens.SyncScreen
import com.example.cayrouniformes.presentation.screens.WelcomeScreen
import com.example.cayrouniformes.presentation.theme.CayroUniformesTheme

class MainActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CayroUniformesApp()
        }
    }
}

@Composable
fun CayroUniformesApp() {
    CayroUniformesTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "welcome"
        ) {
            composable("welcome") {
                WelcomeScreen(navController)
            }

            composable("sync") {
                SyncScreen(navController)
            }

            composable("notifications") {
                NotificationsScreen(navController)
            }

            composable(
                "order/{orderId}",
                arguments = listOf(navArgument("orderId") { type = NavType.StringType })
            ) { backStackEntry ->
                val orderId = backStackEntry.arguments?.getString("orderId") ?: "0"
                OrderDetailScreen(navController, orderId)
            }
        }
    }
}