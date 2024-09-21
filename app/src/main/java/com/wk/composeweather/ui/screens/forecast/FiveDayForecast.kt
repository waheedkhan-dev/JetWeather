package com.wk.composeweather.ui.screens.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wk.composeweather.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiveDayForecast(modifier: Modifier = Modifier,onBackClick: () -> Unit = {}) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string._5_days_forecast),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    ), modifier = modifier.padding(horizontal = 8.dp)
                )
            },
            navigationIcon = {
                Icon(modifier = modifier.padding(vertical = 4.dp).clickable {
                    onBackClick()
                }, imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription =  null)
            },
            modifier = modifier.shadow(2.dp),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )
    }) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Five Day Forecast")
        }
    }
}