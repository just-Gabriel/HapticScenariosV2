package fr.maloof.hapticscenariosv2.ui.scenario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.utils.ScenarioController

@Composable
fun StartScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        val random = ScenarioController.getRandomScenario()
        navController.navigate(random)
    }
}
