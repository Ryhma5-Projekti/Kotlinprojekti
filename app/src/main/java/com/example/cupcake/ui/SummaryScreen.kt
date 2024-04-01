package com.example.cupcake.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cupcake.data.ColorLibrary

/**
 * This composable expects [orderUiState] that represents the order state, [onCancelButtonClicked]
 * lambda that triggers canceling the order and passes the final order to [onSendButtonClicked]
 * lambda
 */
@Composable
fun OrderSummaryScreen(
    logo: Painter,
    bcrColor: String,
    logoColor: String,
    text: String,
    textColor: String,
    modifier: Modifier = Modifier
) {
    /**
    val colorHex = getColorHex(logoColor)
     */
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 50.dp)
            .background(color = getColorHex(bcrColor))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = logo,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterVertically)
                    .offset(x = 22.dp),
                colorFilter = ColorFilter.tint(getColorHex(logoColor))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 36.sp,
                    color = getColorHex(textColor),
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }

    }
}

private fun getColorHex(colorName: String): Color {
    return ColorLibrary.colors[colorName] ?: Color.Black // Return black if color not found
}