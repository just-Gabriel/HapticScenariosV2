package fr.maloof.hapticscenariosv2.ui.scenario

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.network.DataModel
import fr.maloof.hapticscenariosv2.utils.ScenarioController

@Composable
fun StartScreen(navController: NavController, userId: DataModel.User?, telephoneId: DataModel.Telephone?) {
    val user = navController.previousBackStackEntry?.savedStateHandle?.get<DataModel.User>("user")
    val telephone = navController.previousBackStackEntry?.savedStateHandle?.get<DataModel.Telephone>("telephone")

    if (user != null && telephone != null) {
        LaunchedEffect(Unit) {
            val random = ScenarioController.getRandomScenario()
            navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
            navController.currentBackStackEntry?.savedStateHandle?.set("telephone", telephone)
            navController.navigate(random)
        }
    } else {
        Text("Erreur : utilisateur ou téléphone non transmis")
    }
}

