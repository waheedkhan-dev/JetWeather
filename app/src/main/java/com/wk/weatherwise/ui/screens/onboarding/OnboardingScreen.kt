package com.wk.weatherwise.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wk.weatherwise.R
import com.wk.weatherwise.ui.theme.robotoFamily

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onPermissionRequest: () -> Unit,
    onManualSearch: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize().background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top), // Space elements evenly from top
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Spacer to push the content down a bit
        item {
            Spacer(modifier = Modifier.height(120.dp))  // Adjust the height to control how much to push down
        }

        // Weather Image
        item {
            Image(
                painter = painterResource(R.drawable.weather),
                contentDescription = "weather_image",
                modifier = Modifier.size(150.dp) // Adjust the image size as per requirement
            )
        }

        // Heading
        item {
            Text(
                "Location and Your Weather",
                style = TextStyle(
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.padding(horizontal = 12.dp)  // Padding for the text
            )
        }

        // Description Text
        item {
            Text(
                text = "Our app uses your device's location data to deliver accurate local weather updates, alerts, and personalized features. Granting location permission allows us to provide the most up-to-date weather information based on your current location.\n\n" +
                        "If you prefer not to share your location, you can still access weather updates by manually adding your desired location within the app.",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    fontFamily = robotoFamily,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp)  // Horizontal padding for the description
                    .fillMaxWidth() // Ensure the text wraps correctly
            )
        }

        // Spacer to create more room before buttons
        item {
            Spacer(modifier = Modifier.height(12.dp))  // Adjust this height as per your preference
        }

        // Location Access Button
        item {
            Button(
                onClick = { onPermissionRequest() },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp),  // Uniform button height
            ) {
                Text(
                    "Allow Location Access",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = robotoFamily
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        // "Or" Text
        item {
            Text(
                "Or",
                style = TextStyle(
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        // Manual Search Button
        item {
            Button(
                onClick = { onManualSearch() },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp),
            ) {
                Text(
                    "Manual Search",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = robotoFamily
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun OnboardingScreenPreview(modifier: Modifier = Modifier) {
    OnboardingScreen(onPermissionRequest = {}, onManualSearch = {})
}