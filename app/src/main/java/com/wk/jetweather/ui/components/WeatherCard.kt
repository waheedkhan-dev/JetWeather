package com.wk.jetweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.wk.jetweather.ui.theme.JetWeatherTheme
import com.wk.jetweather.ui.theme.robotoFamily
import com.wk.jetweather.utils.previewers.CurrentWeatherProvider

@Composable
fun WeatherCard(modifier: Modifier = Modifier, currentWeatherEntity: CurrentWeatherEntity) {
    Box(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth()
            .background(color = Color.LightGray.copy(alpha = .5f), shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.weather),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal
                )
            )
            Row(
                modifier = modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = modifier.size(14.dp),
                    painter = painterResource(R.drawable.loc_icon),
                    contentDescription = "location"
                )
                Text(
                    modifier = modifier,
                    text = currentWeatherEntity.cityName,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Light
                    )
                )
            }
            Spacer(modifier = modifier.padding(vertical = 18.dp))
            Row(
                modifier = modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${currentWeatherEntity.temp.toInt()}°", style = TextStyle(
                        fontSize = 42.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = currentWeatherEntity.description, style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Normal
                    ), modifier = modifier.padding(vertical = 8.dp)
                )
            }
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://openweathermap.org/img/wn/${currentWeatherEntity.icon}@4x.png")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .padding(end = 12.dp)
                .align(Alignment.CenterEnd)
        )

    }
}


@Preview(showBackground = true)
@Composable
private fun WeatherCardPreview(@PreviewParameter(CurrentWeatherProvider::class) currentWeatherEntity: CurrentWeatherEntity) {
    JetWeatherTheme {
        WeatherCard(currentWeatherEntity = currentWeatherEntity)
    }
}