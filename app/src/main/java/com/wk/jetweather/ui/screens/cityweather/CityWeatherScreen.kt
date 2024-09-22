package com.wk.jetweather.ui.screens.cityweather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.wk.jetweather.data.datasource.local.entities.CurrentWeatherEntity
import com.wk.jetweather.ui.components.SearchTextField
import com.wk.jetweather.ui.components.WeatherCard
import com.wk.jetweather.ui.theme.JetWeatherTheme
import com.wk.jetweather.utils.previewers.CurrentWeatherProvider

@Composable
fun CityWeatherScreen(modifier: Modifier = Modifier, allWeathers: List<CurrentWeatherEntity>,onSearchAction: (String) -> Unit = {}) {
    Column(
        modifier = modifier.fillMaxSize(),
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
        LazyColumn {
            items(allWeathers, key = { it.id }) {
                WeatherCard(currentWeatherEntity = it)
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