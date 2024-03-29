package com.example.cupcake.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cupcake.R

/**
 * Composable komponentti joka sallii käyttäjän valita halutun määrän tuotteita ja odottaa
 * [onNextButtonClicked] lambdaa joka odottaa valittua määrää ja navigoi seuraavaan näkymään
 */
@Composable
fun StartOrderScreen(
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier       /**  Komponentin ulkoasun muokkaamiseen tarvittavat Modifier -objektit */
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        /** Ensimmäinen column (Logo-kuva ja teksti) */
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.width(300.dp)
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Text(
                text = stringResource(R.string.alkuteksti),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        }

        /** toinen column (napit) */
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_medium)
            )
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onNextButtonClicked
            ) {
                Text(stringResource(R.string.next))
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
    }
}
