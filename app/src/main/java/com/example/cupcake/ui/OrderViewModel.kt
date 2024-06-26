/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake.ui

import androidx.lifecycle.ViewModel
import com.example.cupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/** Yhden kuppikakun hinta */
private const val PRICE_PER_CUPCAKE = 2.00

/** Lisämaksu saman päivän valinnalle */
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

/**
 * [OrderViewModel] holds information about a cupcake order in terms of quantity, flavor, and
 * pickup date. It also knows how to calculate the total price based on these order details.
 */
class OrderViewModel : ViewModel() {

    /**
     * Tehdyt valinnat nykyiselle tilaukselle
     */
    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    /**
     * Set the quantity [numberCupcakes] of cupcakes for this order's state and update the price
     */
    fun setQuantity(numberCupcakes: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = numberCupcakes,
                price = calculatePrice(quantity = numberCupcakes)
            )
        }
    }

    /**
     * Asettaa [desiredColour] tämän tilauksen valintoihin.
     * Vain yhden värin voi valita
     */
    fun setColour(desiredColour: String) {
        _uiState.update { currentState ->
            currentState.copy(colour = desiredColour)   /** Asettaa halutun värivalinnan arvoon colour (OrderUiState.kt) */
        }
    }

    fun getColour(): String {
        return _uiState.value.colour
    }

    fun setColour2(desiredColour: String) {
        _uiState.update { currentState ->
            currentState.copy(colour2 = desiredColour)   /** Asettaa halutun värivalinnan arvoon colour (OrderUiState.kt) */
        }
    }

    fun getColour2():String {
        return _uiState.value.colour2
    }

    fun setColours(desiredColours: List<String?>) {
        _uiState.update { currentState->
            currentState.copy(colours = desiredColours)
        }
    }

    fun getColours(): List<String?> {
        return _uiState.value.colours
    }

     /** Asettaa [pickupDate] nykyiselle tilaukselle */

    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                colour2 = pickupDate,
                price = calculatePrice(pickupDate = pickupDate)
            )
        }
    }

    /**
     * tilauksen resettaus
     */
    fun resetOrder() {
        _uiState.value = OrderUiState(pickupOptions = pickupOptions())
    }

    /**
     * palauttaa lasketun hinnan tilauksen tietojen perusteella
     */
    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity,
        pickupDate: String = _uiState.value.colour2
    ): String {
        var calculatedPrice = quantity * PRICE_PER_CUPCAKE
        // Jos valittiin saman päivän kuljetus
        if (pickupOptions()[0] == pickupDate) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatedPrice)
        return formattedPrice
    }

    /**
     * Palauttaa listan pvm vaihtoehdoista, tämä päivä ja 3 seuraavaa
     */
    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // lisää tämän ja 3 seuraavaa päivää
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }
}
