package fr.maloof.hapticscenariosv2.ui.sliders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.network.DataModel
import fr.maloof.hapticscenariosv2.network.ServiceLocator
import fr.maloof.hapticscenariosv2.utils.ScenarioController
import fr.maloof.hapticscenariosv2.utils.TestProgressController
import fr.maloof.hapticscenariosv2.utils.VibrationManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SliderScreen(
    navController: NavController,
    vibrationId: Int,
    nextScenario: String? = null,
    user: DataModel.User,
    telephone: DataModel.Telephone
) {
    val context = LocalContext.current
    val vibrationManager = remember { VibrationManager(context) }

    var lentRapide by remember { mutableStateOf(2) }
    var echecSucces by remember { mutableStateOf(2) }
    var peuBeaucoup by remember { mutableStateOf(2) }
    var diminutionAugmentation by remember { mutableStateOf(2) }
    var douxTranchant by remember { mutableStateOf(2) }

    val scenarioEvaluated = nextScenario ?: ScenarioController.getRandomScenario()

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF4F9FC)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Évaluation de la vibration", fontSize = 22.sp, color = Color(0xFF019AAF))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Tests restants : ${TestProgressController.remaining()}", fontSize = 18.sp, color = Color(0xFF019AAF))
                Spacer(modifier = Modifier.height(32.dp))

                ThreeStateToggle("Lent", "Rapide", lentRapide) { lentRapide = it }
                ThreeStateToggle("Échec", "Succès", echecSucces) { echecSucces = it }
                ThreeStateToggle("Peu", "Beaucoup", peuBeaucoup) { peuBeaucoup = it }
                ThreeStateToggle("Diminution", "Augmentation", diminutionAugmentation) { diminutionAugmentation = it }
                ThreeStateToggle("Doux", "Tranchant", douxTranchant) { douxTranchant = it }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        TestProgressController.increment()

                        println("🟩 [DEBUG] Vibration évaluée : $vibrationId")
                        println("🟩 [DEBUG] Scénario : $scenarioEvaluated")
                        println("🟩 [DEBUG] Réponses : L/R=$lentRapide, E/S=$echecSucces, P/B=$peuBeaucoup, D/A=$diminutionAugmentation, D/T=$douxTranchant")
                        println("🟩 [DEBUG] User ID : ${user.id}, Tel ID : ${telephone.id}")


                        val experience = DataModel.EmotionalExperience(
                            user = "/api/users/${user.id}",
                            telephone = "/api/telephones/${telephone.id}",
                            slider1 = lentRapide,
                            slider2 = echecSucces,
                            slider3 = peuBeaucoup,
                            slider4 = diminutionAugmentation,
                            slider5 = douxTranchant,
                            scenario = scenarioEvaluated,
                            vibrationId = vibrationId,
                            mobile = 0
                        )

                        println("🟨 [ENVOI] Envoi de l'expérience : $experience")

                        ServiceLocator.apiService.postEmotionalExperience(experience)
                            .enqueue(object : Callback<DataModel.EmotionalExperience> {
                                override fun onResponse(
                                    call: Call<DataModel.EmotionalExperience>,
                                    response: Response<DataModel.EmotionalExperience>
                                ) {
                                    if (response.isSuccessful) {
                                        println("✅ Expérience enregistrée : ${response.body()}")
                                        val next = if (TestProgressController.isFinished()) "end" else scenarioEvaluated
                                        println("➡️ Navigation vers $next")

                                        navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
                                        navController.currentBackStackEntry?.savedStateHandle?.set("telephone", telephone)

                                        navController.navigate(next)
                                    } else {
                                        println("❌ Erreur HTTP (${response.code()}): ${response.errorBody()?.string()}")
                                    }
                                }

                                override fun onFailure(call: Call<DataModel.EmotionalExperience>, t: Throwable) {
                                    println("🔥 Envoi échoué : ${t.message}")
                                }
                            })
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF019AAF),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .width(220.dp)
                        .height(56.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp))
                ) {
                    Text("Suivant", fontSize = 16.sp)
                }
            }
        }
    }
}


@Composable
fun ThreeStateToggle(
    leftLabel: String,
    rightLabel: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (1..3).forEach { position ->
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (value == position) Color(0xFF019AAF) else Color.White)
                        .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                        .clickable { onValueChange(position) },
                    contentAlignment = Alignment.Center
                ) {}
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(leftLabel, color = Color.Gray, fontSize = 12.sp)
            Text("Sans avis", color = Color.Gray, fontSize = 12.sp)
            Text(rightLabel, color = Color.Gray, fontSize = 12.sp)
        }
    }
}
