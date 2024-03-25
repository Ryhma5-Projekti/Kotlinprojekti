package com.example.cupcake.data

/**
 * Data class that represents the current UI state in terms of [quantity], [colour],
 * [dateOptions], selected pickup [colour2] and [price]
 */
data class OrderUiState(
    /** Valittu määrä */
    val quantity: Int = 0,
    /** värivaihtoehdot katso data/DataSource */
    val colour: String = "",
    /** Valittu päivämäärä */
    val colour2: String = "",
    val colours: List<String?> = listOf(),
    /** Tilauksen kokonaiskustannus */
    val price: String = "",
    /** Mahdolliset päivämäärät*/
    val pickupOptions: List<String> = listOf()
)
