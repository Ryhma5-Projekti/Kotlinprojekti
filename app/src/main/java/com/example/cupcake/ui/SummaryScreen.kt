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