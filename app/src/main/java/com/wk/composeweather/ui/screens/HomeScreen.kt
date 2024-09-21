package com.wk.composeweather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.wk.composeweather.R
import com.wk.composeweather.ui.components.EnvironmentalConditions
import com.wk.composeweather.ui.screens.uistate.HomeScreenUiState
import com.wk.composeweather.ui.theme.JetWeatherTheme
import com.wk.composeweather.utils.CommonFunctions
import com.wk.composeweather.utils.CommonFunctions.convertTimestampToUTCFormat
import com.wk.composeweather.utils.CommonFunctions.windDirection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, homeScreenUiState: HomeScreenUiState) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(stringResource(R.string.app_name)) },modifier = modifier.shadow(2.dp))
    }) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top
        ) {
            when (homeScreenUiState) {
                is HomeScreenUiState.Loading -> {
                    Text(text = "Loading")
                }

                is HomeScreenUiState.Success -> {
                    val currentWeather = homeScreenUiState.currentWeather
                    Box(modifier = modifier.fillMaxWidth().height(350.dp).background(color = Color.White).padding(16.dp)) {
                        Column(modifier = modifier.fillMaxWidth()) {
                            Text(text = currentWeather.name, style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium
                            ))
                            Text(text = convertTimestampToUTCFormat(currentWeather.dt.toLong()), style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal
                            ))
                        }
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://openweathermap.org/img/wn/${currentWeather.weather.first().icon}@4x.png")
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(300.dp)
                                .align(Alignment.CenterEnd)
                                .clip(RoundedCornerShape(6.dp))
                                .clickable {
                                }
                        )
                        Column(modifier = modifier.fillMaxWidth().align(Alignment.BottomStart)) {
                            Text(text = "${currentWeather.main.temp.toInt()}°c", style = TextStyle(
                                fontSize = 42.sp,
                                fontWeight = FontWeight.Bold
                            ))
                            Text(text = currentWeather.weather.first().main, style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal
                            )
                            )
                        }
                    }
                    Box(modifier = modifier.padding(vertical = 12.dp, horizontal = 8.dp).background(color = Color.White, shape = RoundedCornerShape(8.dp)).border(1.dp, color = Color.LightGray).padding(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "temp_high")
                            Text(text = "High : ${currentWeather.main.temp_max.toInt()}°c")
                            Text(text = "|", modifier = modifier.padding(horizontal = 4.dp))
                            Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "temp_low")
                            Text(text = "Low : ${currentWeather.main.temp_min.toInt()}°c")
                        }
                    }
                    Spacer(modifier.size(16.dp))
                    Row(modifier = modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                        EnvironmentalConditions(title = "Humidity Percent", value = "${currentWeather.main.humidity}%", icon = painterResource(R.drawable.humidity_high_24px))
                        EnvironmentalConditions(title = "Wind Speed", value = "${currentWeather.wind.speed.toInt()}km/h", icon = painterResource(R.drawable.windy))
                    }
                    Spacer(modifier.size(12.dp))
                    Row(modifier = modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                        EnvironmentalConditions(title = "Surface Pressure", value = "${currentWeather.main.pressure}hpa", icon = painterResource(R.drawable.surface_pressure))
                        EnvironmentalConditions(title = "Wind Direction", value = windDirection(degrees = currentWeather.wind.deg.toDouble()), icon = painterResource(R.drawable.wind_rose))
                    }
                }

                is HomeScreenUiState.Error -> {
                    Text(text = homeScreenUiState.errorMessage)
                }

                else -> {}
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    JetWeatherTheme {
        HomeScreen(homeScreenUiState = HomeScreenUiState.Success(CommonFunctions.getCurrentWeather()))
    }
}