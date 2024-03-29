package com.example.cupcake.data

/**
 * Data class that represents the current UI state in terms of [quantity], [colour],
 * [dateOptions], selected pickup [colour2] and [price]
 */
data class OrderUiState(
    val quantity: Int = 0,
    val colour: String = "",
    val colour2: String = "",
    val colourMix: String ="",


    val price: String = "",
    val pickupOptions: List<String> = listOf()
)
