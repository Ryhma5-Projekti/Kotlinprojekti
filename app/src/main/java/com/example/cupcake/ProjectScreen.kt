package com.example.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen

/**
 * enum arvot löytyvät polusta res/drawable/strings.xml jossa niiden arvoja voi muuttaa
 */
enum class ProjectScreen(@StringRes val title: Int) {
    Screen1(title = R.string.app_name),
    Screen2(title = R.string.screen2),
    Screen3(title = R.string.screen3),
    Screen4(title = R.string.screen4)
}

/**
 * alla navbarin koodi jossa lukee näytön otsikko ja on mahdollisuus navigoida taaksepäin
 */
@Composable
fun AppBar(
    currentScreen: ProjectScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun KotlinApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = ProjectScreen.valueOf(
        backStackEntry?.destination?.route ?: ProjectScreen.Screen1.name
    )

    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = ProjectScreen.Screen1.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            /** Aloitusnäyttö */
            composable(route = ProjectScreen.Screen1.name) {
                StartOrderScreen(
                 //   quantityOptions = DataSource.colours2,
                    onNextButtonClicked = {
                        navController.navigate(ProjectScreen.Screen2.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            /** Näyttö 2 */
            composable(route = ProjectScreen.Screen2.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    onNextButtonClicked = { navController.navigate(ProjectScreen.Screen3.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    /** Hakee värivaihtoehdot listasta id:n perusteella (DataSource.kt) ja asettaa ne options listaan */
                    options = DataSource.colours.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setColour(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            /** Näyttö 3 */
            composable(route = ProjectScreen.Screen3.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    onNextButtonClicked = { navController.navigate(ProjectScreen.Screen4.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = DataSource.colours3.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setColour2(it) },
                    modifier = Modifier.fillMaxHeight(),

                )
            }
            /** Näyttö4 */
            composable(route = ProjectScreen.Screen4.name) {

                val context = LocalContext.current
                OrderSummaryScreen(         /** Käytetään OrderSummaryScreen-komponenttia näyttämään tilausten yhteenveto. */
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String ->
                        shareOrder(context, subject = subject, summary = summary)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}

/**
 *  funktio palauttaa tilauksen alkutilaan ja siirtyy takaisin aloitusnäyttöön
 */
private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(ProjectScreen.Screen1.name, inclusive = false)
}

/**
 * funktio luo ja käynnistää aikeen tilauksen jakamiseksi.
 */
private fun shareOrder(context: Context, subject: String, summary: String) {
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_colour_choice)
        )
    )
}
