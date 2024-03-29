package com.example.cupcake.ui

import android.graphics.Rect
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.cupcake.R
import com.example.cupcake.data.OrderUiState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.accompanist.coil.rememberImagePainter


import com.example.cupcake.ui.components.FormattedPriceLabel
/**
 * This composable expects [orderUiState] that represents the order state, [onCancelButtonClicked]
 * lambda that triggers canceling the order and passes the final order to [onSendButtonClicked]
 * lambda
 */
@Composable
fun OrderSummaryScreen(
    backgroundColor: Color,
    logo: Painter, // Use Painter for the logo
    logoColor: Color, // Add color to the logo
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(16.dp)
    ) {
        // Draw the logo with the background color so we can change its color
        Image(
            painter = rememberImagePainter(
                painter = logo,
                colorFilter = ColorFilter.tint(logoColor)
            ),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp) // Adjust size as needed
                .align(Alignment.Center)
        )
        // Text
        Text(
            text = text,
            color = textColor,
            style = TextStyle(fontSize = 18.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
} }
}
/**
@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,   /** Palaa alkuun */
    onSendButtonClicked: (String, String) -> Unit,      /** Palaa alkuun */
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources

    val numberOfCupcakes = resources.getQuantityString(
        R.plurals.cupcakes,
        orderUiState.quantity,
        orderUiState.quantity
    )
    /** Ladataan ja muotoillaan merkkijonoresurssi parametrien kanssa. */
    val orderSummary = stringResource(
        R.string.order_details,
        numberOfCupcakes,
        orderUiState.colour,
        orderUiState.colour2,
        orderUiState.quantity
    )
    val newOrder = stringResource(R.string.new_cupcake_order)

    val items = listOf(                 /** Luodaan tilauksen yhteenvetojen lista näyttämistä varten */
        Pair(stringResource(R.string.quantity), numberOfCupcakes),      /** Yhteenveto rivi 1: näyttää valitun määrän */
        Pair(stringResource(R.string.colour), orderUiState.colour),     /** Yhteenveto rivi 2: näyttää valitun värin */
        Pair(stringResource(R.string.colour2), orderUiState.colours)        /** Yhteenveto rivi 3: näyttää toisen valitun värin */
    )

    fun <T> List<T>.permutations(): List<List<T>> {
        val result = mutableListOf<List<T>>()
        for (outer in indices) {
            val temp = mutableListOf<T>()
            for (inner in indices) {
                temp.add(this[(outer + inner) % size])
            }
            result.add(temp)
        }
        return result
    }

    fun <T> List<T>.convertToColors(): List<Color> {
        val result = mutableListOf<Color?>()
        for (item in this) {
            result.add(ColorLibrary.colors[item as String])
        }
        return result.toList().filterNotNull()
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            items.forEach { item ->             /** listan jokaiselle yhteenvedolle oma rivi */
                Text(item.first.uppercase())    /** Tulostaa ensimmäisen elementin, yhteenvedon nimen isoin kirjaimin. */
                if (item.second is String) {
                    Text(text = item.second as String, fontWeight = FontWeight.Bold)
                } else if (item.second is List<*>) {
                    Text(
                        text = (item.second as List<*>)
                            .convertToColors()
                            .permutations()
                            .joinToString("\n\n"),
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(thickness = dimensionResource(R.dimen.thickness_divider))       /** Luo erotusviivan käyttäen määriteltyä paksuutta resurssista. */
            }
            /** Spacer-luokka luo tilan sijoittamiseen. Tässä asetetaan korkeus resurssista määritetyn pienen tyhjätilan korkeuden verran. */
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            /** komponentti, joka näyttää hinnan muotoillusti. */
            /** Tässä annetaan tilausrivin hinta ja lisätään sille muokkain, joka kohdistetaan oikeaan reunaan. */
            FormattedPriceLabel(
                subtotal = orderUiState.price,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                /** Lohko, joka sisältää kaksi nappia, ja joka järjestetään pystysuunnassa määritetyn pienen välitilan avulla. */
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSendButtonClicked(newOrder, orderSummary) }
                ) {
                    /** Painike, joka kutsuu onSendButtonClicked-funktiota uuden tilauksen ja tilausyhteenvedon kanssa. */
                    Text(stringResource(R.string.send))
                }

                OutlinedButton(         /** Reunustettu painike, */
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCancelButtonClicked     /** joka kutsuu onCancelButtonClicked-funktiota */
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}
*/
/**
@Preview
@Composable
fun OrderSummaryPreview() {
    CupcakeTheme {
        OrderSummaryScreen(
            orderUiState = OrderUiState(0, "Test", "Test", "$300.00"),
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            modifier = Modifier.fillMaxHeight()
        )
    }
}
*/