package fr.maloof.hapticscenariosv2.utils

import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.network.DataModel
import fr.maloof.hapticscenariosv2.viewmodel.ScenarioViewModel

fun navigateToSlidersWithTest(
    navController: NavController,
    viewModel: ScenarioViewModel,
    test: DataModel.ScenarioVibration
) {
    // ✅ Stockage dans le ViewModel

    if (viewModel.user.value == null || viewModel.telephone.value == null) {
        println("❌ Impossible de naviguer : données manquantes dans le ViewModel")
        return
    }

    // ✅ Navigation avec arguments fiables
    navController.navigate("sliders/${test.vibrationId}/${test.scenario}")
}






