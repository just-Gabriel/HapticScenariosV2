package fr.maloof.hapticscenariosv2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.maloof.hapticscenariosv2.network.DataModel
import fr.maloof.hapticscenariosv2.ui.form.UserFormScreen
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

                    NavHost(navController = navController, startDestination = "userForm") {

                        // Formulaire utilisateur
                        composable("userForm") {
                            UserFormScreen { user, telephone ->
                                navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
                                navController.currentBackStackEntry?.savedStateHandle?.set("telephone", telephone)
                                navController.navigate("start")
                            }
                        }

                        // Écran de démarrage du test
                        composable("start") {
                            val user = navController.previousBackStackEntry?.savedStateHandle?.get<DataModel.User>("user")
                            val telephone = navController.previousBackStackEntry?.savedStateHandle?.get<DataModel.Telephone>("telephone")

                            if (user != null && telephone != null) {
                                StartScreen(navController, user, telephone)
                            }
                        }

                        // Scénario bouton
                        composable("bouton") {
                            val user = navController.previousBackStackEntry?.savedStateHandle?.get<DataModel.User>("user")
                            val telephone = navController.previousBackStackEntry?.savedStateHandle?.get<DataModel.Telephone>("telephone")

                            if (user != null && telephone != null) {
                                ScenarioButton(navController, user, telephone)
                            }else {
                                Log.e("NAV", "❌ User ou Telephone manquant dans bouton")
                            }
                        }

                        // Scénario drag
                        composable("scenario_drag") {
                            val user = navController.previousBackStackEntry?.savedStateHandle?.get<DataModel.User>("user")
                            val telephone = navController.previousBackStackEntry?.savedStateHandle?.get<DataModel.Telephone>("telephone")

                            if (user != null && telephone != null) {
                                ScenarioDragAndDropScreen(navController, user, telephone)
                            }else {
                                Log.e("NAV", "❌ User ou Telephone manquant dans scenario_drag")
                            }
                        }

                        // Scénario popup
                        composable("scenario_popup") {
                            val user = navController.previousBackStackEntry?.savedStateHandle?.get<DataModel.User>("user")
                            val telephone = navController.previousBackStackEntry?.savedStateHandle?.get<DataModel.Telephone>("telephone")

                            if (user != null && telephone != null) {
                                ScenarioPopupScreen(navController, user, telephone)
                            }else {
                                Log.e("NAV", "❌ User ou Telephone manquant dans scenario_popup")
                            }
                        }

                        // Écran des sliders
                        composable("sliders") {
                            val vibrationId = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("vibrationId") ?: 0
                            val nextScenario = navController.previousBackStackEntry?.savedStateHandle?.get<String>("nextScenario") ?: ""
                            val user = navController.previousBackStackEntry?.savedStateHandle?.get<DataModel.User>("user")
                            val telephone = navController.previousBackStackEntry?.savedStateHandle?.get<DataModel.Telephone>("telephone")

                            if (user != null && telephone != null) {
                                SliderScreen(navController, vibrationId, nextScenario, user, telephone)
                            }
                        }

                        // Fin du test
                        composable("end") {
                            EndScreen()
                        }
                    }
                }
            }
        }
    }
}
