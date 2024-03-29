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
    quantityOptions: List<Pair<Int, Int>>,  /** Lista määrävaihtoehdoista, määritelty ProjectScreen.kt:ssa*/
    onNextButtonClicked: (Int) -> Unit,     /**  Odottaa kokonaislukuarvoa (Int) parametrina ja palauttaa yksikkötyypin (Unit) */
    modifier: Modifier = Modifier       /**  Komponentin ulkoasun muokkaamiseen tarvittavat Modifier -objektit */
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        /** Ensimmäinen column (Kuppikakku-kuva ja teksti) */
        Column(
            modifier = Modifier.fillMaxWidth(),     /** Täyttää koko leveyden */
            horizontalAlignment = Alignment.CenterHorizontally,     /** Asettaa vaakasuuntaisen keskityksen */
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))  /**  ja pystysuuntaisen järjestelyn,
                                                            jossa välit määräytyvät ulkoisesta resurssista (dimens.xml) */
) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))   /** Lisää tyhjää tilaa pystysuunnassa */
            Image(                                              /** näyttää kuvan cupcake */
                painter = painterResource(R.drawable.colormix_logo),  /** (drawable/cupcake.xml) */
                contentDescription = null,                      /** jolla ei ole sisältökuvausta */
                modifier = Modifier.width(300.dp)               /**  ja määrittelee leveyden */
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Text(                                                   /** Näyttää tekstin */
                text = stringResource(R.string.order_cupcakes),           /** joka haetaan resursseista (strings.xml) */
                style = MaterialTheme.typography.headlineSmall    /** käyttäen tiettyä tyylitiedostoa */
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))    /** Lisää tyhjää tilaa pystysuunnassa */
        }

        /** toinen column (määrä vaihtoehdot / niiden napit) */
        Column(
            modifier = Modifier.fillMaxWidth(),     /** Täyttää koko leveyden */
            horizontalAlignment = Alignment.CenterHorizontally,     /** Asettaa vaakasuuntaisen keskityksen */
            verticalArrangement = Arrangement.spacedBy(            /** Ja pystysuuntaisen järjestelyn */
                dimensionResource(id = R.dimen.padding_medium)                      /** jossa välit määräytyvät ulkoisesta resurssista (dimens.xml) */
            )
        ) {
            quantityOptions.forEach { item ->       /** Literoi läpi määrävaihtoehtojen listan */
                SelectQuantityButton(              /** ja luo jokaiselle vaihtoehdolle napin */
                    labelResourceId = item.first,   /** Asettaa napin tekstin resurssitunnisteen perusteella */
                    onClick = { onNextButtonClicked(item.second) },   /** kun valintanappia painetaan sivu vaihtuu automaattisesti */
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

/**
 * Customizable button composable that displays the [labelResourceId]
 * and triggers [onClick] lambda when this composable is clicked
 */
@Composable
fun SelectQuantityButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text(stringResource(labelResourceId))
    }
}

