package fr.maloof.hapticscenariosv2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.maloof.hapticscenariosv2.ui.form.UserFormScreen
import fr.maloof.hapticscenariosv2.ui.scenario.*
import fr.maloof.hapticscenariosv2.ui.sliders.SliderScreen
import fr.maloof.hapticscenariosv2.utils.VibrationManager
import fr.maloof.hapticscenariosv2.viewmodel.ScenarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme()
            ) {
                Surface {
                    val navController: NavHostController = rememberNavController()
                    val vibrationManager = remember { VibrationManager(this) }
                    val viewModel: ScenarioViewModel = viewModel()


                    NavHost(navController = navController, startDestination = "userForm") {

                        // Formulaire utilisateur
                        composable("userForm") {
                            UserFormScreen(
                                vibrationManager = vibrationManager,
                                viewModel = viewModel,
                                onFormSubmit = {
                                    navController.navigate("start")
                                }
                            )
                        }

                        // Démarrage du test
                        composable("start") {
                            StartScreen(navController, viewModel)
                        }


                        // Scénarios
                        composable("bouton") {
                            ScenarioButton(navController, viewModel)
                        }
                        composable("scenario_drag") {
                            ScenarioDragAndDropScreen(navController, viewModel)
                        }
                        composable("scenario_popup") {
                            ScenarioPopupScreen(navController, viewModel)
                        }

                        // Écran sliders
                        composable("sliders/{vibrationId}/{scenario}") { backStackEntry ->
                            val vibrationId = backStackEntry.arguments?.getString("vibrationId")?.toIntOrNull()
                            val scenario = backStackEntry.arguments?.getString("scenario")

                            val user = viewModel.user.value
                            val telephone = viewModel.telephone.value

                            if (vibrationId != null && scenario != null && user != null && telephone != null) {
                                val test = viewModel.findTest(vibrationId, scenario)
                                if (test != null) {
                                    SliderScreen(navController, viewModel)

                                } else {
                                    println("❌ Test introuvable pour vibrationId=$vibrationId / scenario=$scenario")
                                }
                            } else {
                                println("❌ Données manquantes dans SliderScreen")
                            }
                        }

                        // Fin de test
                        composable("test_termine") {
                            EndScreen(navController)
                        }

                    }
                }
            }
        }
    }
}
