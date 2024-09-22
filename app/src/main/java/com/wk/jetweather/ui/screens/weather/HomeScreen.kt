package com.wk.jetweather.ui.screens.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.wk.jetweather.R
import com.wk.jetweather.data.datasource.local.entities.CurrentWeatherEntity
import com.wk.jetweather.ui.components.EnvironmentalConditions
import com.wk.jetweather.ui.screens.weather.uistate.HomeScreenUiState
import com.wk.jetweather.ui.theme.JetWeatherTheme
import com.wk.jetweather.ui.theme.robotoFamily
import com.wk.jetweather.utils.CommonFunctions
import com.wk.jetweather.utils.CommonFunctions.convertTimestampToUTCFormat
import com.wk.jetweather.utils.CommonFunctions.windDirection
import com.wk.jetweather.utils.previewers.CurrentWeatherProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeatherScreen(
    modifier: Modifier = Modifier,
    homeScreenUiState: HomeScreenUiState,
    onFiveDayForecastClick: () -> Unit = {}
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(stringResource(R.string.app_name),style = TextStyle(
                fontSize = 24.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Bold
            )) },
            modifier = modifier.shadow(2.dp),
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
        )
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
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        CircularProgressIndicator()
                    }
                }

                is HomeScreenUiState.Success -> {
                    val currentWeather = homeScreenUiState.currentWeather

                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(320.dp)
                            .background(color = Color.White)
                            .padding(16.dp)
                    ) {
                        Column(modifier = modifier.fillMaxWidth()) {
                            Text(
                                text = currentWeather.cityName, style = TextStyle(
                                    fontSize = 24.sp,
                                    fontFamily = robotoFamily,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Text(
                                text = convertTimestampToUTCFormat(currentWeather.dt.toLong()),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = robotoFamily,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://openweathermap.org/img/wn/${currentWeather.icon}@4x.png")
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(300.dp)
                                .align(Alignment.CenterEnd)
                        )
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                        ) {
                            Text(
                                text = "${currentWeather.temp.toInt()}°c", style = TextStyle(
                                    fontSize = 42.sp,
                                    fontFamily = robotoFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = currentWeather.main, style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = robotoFamily,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                    }
                    Box(
                        modifier = modifier
                            .padding(vertical = 12.dp, horizontal = 8.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, color = Color.LightGray)
                            .padding(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowUp,
                                contentDescription = "temp_high"
                            )
                            Text(text = "High : ${currentWeather.tempMax.toInt()}°c",style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Normal
                            ))
                            Text(text = "|", modifier = modifier.padding(horizontal = 4.dp),style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Normal
                            ))
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = "temp_low"
                            )
                            Text(text = "Low : ${currentWeather.tempMin.toInt()}°c",style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Normal
                            ))
                        }
                    }

                    Row(
                        modifier = modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        EnvironmentalConditions(
                            title = "Humidity Percent",
                            value = "${currentWeather.humidity}%",
                            icon = painterResource(R.drawable.humidity_high_24px)
                        )
                        EnvironmentalConditions(
                            title = "Wind Speed",
                            value = "${currentWeather.speed.toInt()}km/h",
                            icon = painterResource(R.drawable.windy)
                        )
                    }

                    Row(
                        modifier = modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        EnvironmentalConditions(
                            title = "Surface Pressure",
                            value = "${currentWeather.pressure}hpa",
                            icon = painterResource(R.drawable.surface_pressure)
                        )
                        EnvironmentalConditions(
                            title = "Wind Direction",
                            value = windDirection(degrees = currentWeather.deg.toDouble()),
                            icon = painterResource(R.drawable.wind_rose)
                        )
                    }
                    Spacer(modifier = modifier.weight(1f))
                    ElevatedButton(
                        modifier = modifier
                            .padding(vertical = 12.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(),
                        onClick = {
                            onFiveDayForecastClick()
                        }) {
                        Text(
                            text = stringResource(R.string.see_5_days_forecast), style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    Spacer(modifier = modifier.weight(1f))
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
fun CurrentWeatherScreenPreview(@PreviewParameter(CurrentWeatherProvider::class) currentWeatherEntity: CurrentWeatherEntity) {
    JetWeatherTheme {
        CurrentWeatherScreen(homeScreenUiState = HomeScreenUiState.Success(currentWeather = currentWeatherEntity))
    }
}