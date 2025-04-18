package fr.maloof.hapticscenariosv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.maloof.hapticscenariosv2.ui.scenario.*
import fr.maloof.hapticscenariosv2.ui.sliders.SliderScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme()
            ) {
                Surface {
                    val navController: NavHostController = rememberNavController()

                    NavHost(navController = navController, startDestination = "start") {

                        composable("start") { StartScreen(navController) }
                        composable("scenario") { ScenarioButton(navController) }
                        composable("scenario_drag") { ScenarioDragAndDropScreen(navController) }
                        composable("scenario_popup") { ScenarioPopupScreen(navController) }

                        // ✅ Route complète avec vibrationId + nextScenario
                        composable("sliders/{vibrationId}/{nextScenario}") { backStackEntry ->
                            val vibrationId =
                                backStackEntry.arguments?.getString("vibrationId")?.toIntOrNull()
                                    ?: 0
                            val nextScenario = backStackEntry.arguments?.getString("nextScenario")
                            SliderScreen(navController, vibrationId, nextScenario)
                        }

                        composable("end") { EndScreen() }
                    }
                }
            }
        }
    }
}

