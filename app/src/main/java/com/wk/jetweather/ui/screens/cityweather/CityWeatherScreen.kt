package com.wk.jetweather.ui.screens.cityweather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wk.jetweather.R
import com.wk.jetweather.data.datasource.local.entities.CurrentWeatherEntity
import com.wk.jetweather.ui.components.SearchTextField
import com.wk.jetweather.ui.components.WeatherCard
import com.wk.jetweather.ui.theme.JetWeatherTheme
import com.wk.jetweather.ui.theme.robotoFamily
import com.wk.jetweather.utils.previewers.CurrentWeatherProvider

@Composable
fun CityWeatherScreen(modifier: Modifier = Modifier, allWeathers: List<CurrentWeatherEntity>,onSearchAction: (String) -> Unit = {}) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(allWeathers) {
        if (allWeathers.isEmpty()) {
            snackBarHostState.showSnackbar(context.getString(R.string.no_weather_data_available))
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                hint = "Search weather by city name",
                onSearchQueryChanged = { query ->
                    // Handle search query change
                    println("Search Query: $query")
                }, onSearchAction = { query ->
                    onSearchAction(query)
                }
            )
            if(allWeathers.isNotEmpty()){
                LazyColumn {
                    items(allWeathers, key = { it.id }) {
                        WeatherCard(currentWeatherEntity = it)
                    }
                }
            }else {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = stringResource(R.string.no_weather_data_available),style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Medium
                    )
                    )
                }
            }

        }
    }

}


@Preview(showBackground = true)
@Composable
private fun CityWeatherScreenPreview(@PreviewParameter(CurrentWeatherProvider::class) currentWeatherEntity: CurrentWeatherEntity) {
    JetWeatherTheme {
        CityWeatherScreen(allWeathers = listOf(currentWeatherEntity,currentWeatherEntity.copy(id = 2),currentWeatherEntity.copy(id = 3)))

    }
}