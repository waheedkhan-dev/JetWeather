package com.wk.composeweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.wk.composeweather.ui.navigation.JetWeatherNavHost
import com.wk.composeweather.ui.theme.JetWeatherTheme
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
