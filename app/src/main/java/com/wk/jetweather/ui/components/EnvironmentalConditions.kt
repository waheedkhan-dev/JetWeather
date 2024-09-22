package com.wk.jetweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wk.jetweather.R
import com.wk.jetweather.ui.theme.robotoFamily

@Composable
fun EnvironmentalConditions(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: Painter
) {
    Row(modifier = modifier.padding(12.dp),verticalAlignment = Alignment.CenterVertically){
        Icon(
            modifier = modifier
                .size(32.dp)
                .background(shape = CircleShape, color = Color.LightGray)
                .clip(CircleShape)
                .padding(8.dp), painter = icon, contentDescription = title, tint = Color.Black
        )
        Column(modifier = modifier.padding(horizontal = 8.dp)) {
            Text(text = title, style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = robotoFamily,
                fontSize = 12.sp,
                color = Color.Gray
            ))
            Spacer(modifier = modifier.size(4.dp))
            Text(text = value, style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontFamily = robotoFamily,
                fontSize = 16.sp
            ))
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun EnvironmentalConditionsPreview(modifier: Modifier = Modifier) {
    EnvironmentalConditions(
        title = "Humidity Percent",
        value = "50%",
        icon = painterResource(R.drawable.humidity_high_24px)
    )
}