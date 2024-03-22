package com.example.cupcake.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.cupcake.R
import com.example.cupcake.ui.components.FormattedPriceLabel

/**
 * Composable that displays the list of items as [RadioButton] options,
 * [onSelectionChanged] lambda that notifies the parent composable when a new value is selected,
 * [onCancelButtonClicked] lambda that cancels the order when user clicks cancel and
 * [onNextButtonClicked] lambda that triggers the navigation to next screen
 */
@Composable
/** Komponentti, joka luo valintanäytön */
fun SelectOptionScreen(
    subtotal: String,                                   /** Tilauksen väliaikainen summa */
    options: List<String>,                              /** Lista vaihtoehdoista */
    onSelectionChanged: (String) -> Unit = {},          /** Toiminto valinnan muuttuessa, oletuksena tyhjä */
    onCancelButtonClicked: () -> Unit = {},             /** Toiminto peruuta-painikkeen klikkaukselle, oletuksena tyhjä */
    onNextButtonClicked: () -> Unit = {},               /** Toiminto seuraava-painikkeen klikkaukselle, oletuksena tyhjä */
    modifier: Modifier = Modifier                       /** Ulkoasun muokkaukseen käytettävä Modifier-objekti, oletuksena tyhjä */
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }    /** Tilan säilyttäminen valitulle arvolle */

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            /** Iteroi läpi vaihtoehdot ja luo jokaiselle rivin */
            options.forEach { item ->
                Row(
                    /** Asettaa rivin valittavaksi, määrittelee toiminnon valinnan muuttumiselle */
                    modifier = Modifier.selectable(
                        selected = selectedValue == item,   /** Tarkistaa, onko nykyinen arvo valittu */
                        onClick = {
                            selectedValue = item            /** Asettaa valitun arvon */
                            onSelectionChanged(item)        /** Kutsuu toimintoa valinnan muuttuessa / asettaa valinnan muistiin */
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /** Luo radiopainikkeen ja tekstin jokaiselle riville */
                    RadioButton(
                        selected = selectedValue == item,   /** Asettaa radiopainikkeen valituksi, jos arvo on valittu */
                        onClick = {
                            selectedValue = item            /** Asettaa valitun arvon */
                            onSelectionChanged(item)        /** Kutsuu toimintoa valinnan muuttuessa */
                        }
                    )
                    Text(item)          /** Näyttää vaihtoehdon tekstin */
                }
            }
            Divider(
                thickness = dimensionResource(R.dimen.thickness_divider),
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )
            /** Tämän hetkisen summan näyttävä osuus */
            FormattedPriceLabel(
                subtotal = subtotal,    /** Tämän hetkinen summa */
                modifier = Modifier
                    .align(Alignment.End)     /** Tyyli */
                    .padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
            )
        }
        /** Alarivi jossa kaksi nappia */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedButton(                         /** Nappi */
                modifier = Modifier.weight(1f),
                onClick = onCancelButtonClicked     /** jota painamalla suorittaa funktion onCancelButtonClicked */
            ) {
                Text(stringResource(R.string.cancel))      /** ja jossa lukee cancel */
            }
            Button(                                 /** Nappi */
                modifier = Modifier.weight(1f),

                enabled = selectedValue.isNotEmpty(),   /** joka toimii vasta kun asiakas on tehnyt valinnan */
                onClick = onNextButtonClicked           /** kun nappia painaa se ajaa funktion onNextButtonClicked */
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }

}



/**
@Preview
@Composable
fun SelectOptionPreview() {
    CupcakeTheme {
        SelectOptionScreen(
            subtotal = "299.99",
            options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
            modifier = Modifier.fillMaxHeight()
        )
    }
}
*/