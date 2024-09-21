package com.wk.composeweather.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.wk.composeweather.R
import com.wk.composeweather.ui.screens.uistate.HomeScreenUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, homeScreenUiState: HomeScreenUiState) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(stringResource(R.string.app_name)) })
    }) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (homeScreenUiState) {
                is HomeScreenUiState.Loading -> {
                    Text(text = "Loading")
                }

                is HomeScreenUiState.Success -> {
                    val currentWeather = homeScreenUiState.currentWeather
                    Text(text = currentWeather.name)
                }

                is HomeScreenUiState.Error -> {
                    Text(text = homeScreenUiState.errorMessage)
                }

                else -> {}
            }
        }
    }
}