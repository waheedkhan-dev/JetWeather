package com.wk.jetweather.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.wk.jetweather.ui.navigation.JetWeatherNavHost
import com.wk.jetweather.ui.theme.JetWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetWeatherTheme {
                JetWeatherNavHost()
            }
        }
    }
}

object Graph {
    const val MAIN = "main_graph"
    const val DETAIL = "detail_graph"
}