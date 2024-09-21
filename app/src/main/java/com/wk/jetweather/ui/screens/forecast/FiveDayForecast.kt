package com.wk.jetweather.ui.screens.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.wk.jetweather.R
import com.wk.jetweather.ui.screens.forecast.uistate.ForecastScreenUiState
import com.wk.jetweather.utils.CommonFunctions.convertTimestampToDayName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiveDayForecast(
    modifier: Modifier = Modifier,
    forecastUiState: ForecastScreenUiState,
    onBackClick: () -> Unit = {}
) {
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
                Icon(
                    modifier = modifier
                        .padding(vertical = 4.dp)
                        .clickable {
                            onBackClick()
                        },
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
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
            when (forecastUiState) {
                is ForecastScreenUiState.Loading -> {}
                is ForecastScreenUiState.Success -> {
                    val forecastList = forecastUiState.fiveDayForecast
                    LazyColumn(
                        modifier = modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(forecastList, key = { it.id }) {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(
                                    text = convertTimestampToDayName(it.id), style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                                Spacer(modifier = modifier.weight(1f))
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("https://openweathermap.org/img/wn/${it.icon}@4x.png")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(50.dp)
                                )
                                Text(
                                    text = it.main, style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    ))
                                Spacer(modifier = modifier.weight(1f))
                                Text(
                                    text = "${it.tempMax.toInt()}°c / ${it.tempMin.toInt()}°c", style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    ))

                            }
                        }

                    }


                }

                is ForecastScreenUiState.Error -> {
                    Text(text = forecastUiState.errorMessage)
                }

                is ForecastScreenUiState.InitialState -> {}

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun FiveDayForecastPreview(modifier: Modifier = Modifier) {
    FiveDayForecast(forecastUiState = ForecastScreenUiState.InitialState, onBackClick = {})
}