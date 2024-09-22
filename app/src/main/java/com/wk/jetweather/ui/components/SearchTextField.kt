package com.wk.jetweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wk.jetweather.ui.theme.robotoFamily

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    hint: String = "Search...",
    onSearchQueryChanged: (String) -> Unit,
    onSearchAction: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(56.dp)
            .background(Color.LightGray.copy(alpha = .5f), shape = MaterialTheme.shapes.small)
            .padding(horizontal = 16.dp)
    ) {
        // Search Icon
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Search Text Field
        Box(modifier = Modifier.weight(1f)) {
            if (searchQuery.isEmpty()) {
                Text(
                    text = hint,
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Gray,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Light
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }

            BasicTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    onSearchQueryChanged(it)
                },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,  // Set the keyboard type to text
                    imeAction = ImeAction.Search  // Set the IME action to Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchAction(searchQuery)
                        searchQuery = ""
                        keyboardController?.hide()  // Hide the keyboard after searching
                    }
                )
            )
        }
    }
}