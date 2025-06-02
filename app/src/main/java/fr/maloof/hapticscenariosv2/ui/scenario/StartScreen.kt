package fr.maloof.hapticscenariosv2.ui.scenario

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.viewmodel.ScenarioViewModel

@Composable
fun StartScreen(
    navController: NavController,
    viewModel: ScenarioViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        if (viewModel.prepareNextTest()) {
            val test = viewModel.getCurrentTest()
            Log.i("TEST", "🧪 Test prêt : ${test?.scenario} - vib ${test?.vibrationId}")
            navController.navigate(test!!.scenario)
        } else {
            navController.navigate("test_termine")
        }

    }
}
